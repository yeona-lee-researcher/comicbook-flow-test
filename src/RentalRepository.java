import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 대여 기록 데이터 처리 Repository
 * 대여/반납 관련 모든 DB 작업을 담당
 */
public class RentalRepository {
    
    private ComicRepository comicRepository = new ComicRepository();
    
    /**
     * 대여 처리 (트랜잭션 적용)
     */
    public int rentComic(int comicId, int memberId) {
        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;
        ResultSet generatedKeys = null;
        
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // 트랜잭션 시작
            
            // 1. 만화책이 이미 대여 중인지 확인
            String checkSql = "SELECT is_rented FROM comic WHERE id = ?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, comicId);
            rs = checkStmt.executeQuery();
            
            if (!rs.next()) {
                System.out.println("존재하지 않는 만화책입니다.");
                conn.rollback();
                return -1;
            }
            
            if (rs.getBoolean("is_rented")) {
                System.out.println("이미 대여 중인 만화책입니다.");
                conn.rollback();
                return -1;
            }
            
            // 2. 대여 기록 추가
            String insertSql = "INSERT INTO rental (comic_id, member_id) VALUES (?, ?)";
            insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, comicId);
            insertStmt.setInt(2, memberId);
            
            int insertResult = insertStmt.executeUpdate();
            if (insertResult == 0) {
                System.out.println("대여 기록 추가에 실패했습니다.");
                conn.rollback();
                return -1;
            }
            
            // 3. 만화책 대여 상태 변경
            String updateSql = "UPDATE comic SET is_rented = TRUE WHERE id = ?";
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, comicId);
            
            int updateResult = updateStmt.executeUpdate();
            if (updateResult == 0) {
                System.out.println("만화책 상태 변경에 실패했습니다.");
                conn.rollback();
                return -1;
            }
            
            // 4. 생성된 대여 ID 가져오기
            generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int rentalId = generatedKeys.getInt(1);
                conn.commit(); // 트랜잭션 커밋
                return rentalId;
            }
            
            conn.rollback();
            
        } catch (SQLException e) {
            System.err.println("대여 처리 오류: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("롤백 오류: " + ex.getMessage());
                }
            }
        } finally {
            DBUtil.closeResultSet(generatedKeys);
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(updateStmt);
            DBUtil.closeStatement(insertStmt);
            DBUtil.closeStatement(checkStmt);
            
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // 자동 커밋 복원
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Connection 해제 오류: " + e.getMessage());
                }
            }
        }
        
        return -1; // 실패시 -1 반환
    }
    
    /**
     * 반납 처리 (트랜잭션 적용)
     */
    public boolean returnComic(int rentalId) {
        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement updateRentalStmt = null;
        PreparedStatement updateComicStmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // 트랜잭션 시작
            
            // 1. 대여 기록 확인 (이미 반납되었는지 체크)
            String checkSql = "SELECT comic_id, return_date FROM rental WHERE id = ?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, rentalId);
            rs = checkStmt.executeQuery();
            
            if (!rs.next()) {
                System.out.println("존재하지 않는 대여 기록입니다.");
                conn.rollback();
                return false;
            }
            
            if (rs.getString("return_date") != null) {
                System.out.println("이미 반납된 대여 기록입니다.");
                conn.rollback();
                return false;
            }
            
            int comicId = rs.getInt("comic_id");
            
            // 2. 대여 기록에 반납일 업데이트
            String updateRentalSql = "UPDATE rental SET return_date = CURRENT_DATE WHERE id = ?";
            updateRentalStmt = conn.prepareStatement(updateRentalSql);
            updateRentalStmt.setInt(1, rentalId);
            
            int rentalUpdateResult = updateRentalStmt.executeUpdate();
            if (rentalUpdateResult == 0) {
                System.out.println("반납 기록 업데이트에 실패했습니다.");
                conn.rollback();
                return false;
            }
            
            // 3. 만화책 대여 상태 변경
            String updateComicSql = "UPDATE comic SET is_rented = FALSE WHERE id = ?";
            updateComicStmt = conn.prepareStatement(updateComicSql);
            updateComicStmt.setInt(1, comicId);
            
            int comicUpdateResult = updateComicStmt.executeUpdate();
            if (comicUpdateResult == 0) {
                System.out.println("만화책 상태 변경에 실패했습니다.");
                conn.rollback();
                return false;
            }
            
            conn.commit(); // 트랜잭션 커밋
            return true;
            
        } catch (SQLException e) {
            System.err.println("반납 처리 오류: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("롤백 오류: " + ex.getMessage());
                }
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(updateComicStmt);
            DBUtil.closeStatement(updateRentalStmt);
            DBUtil.closeStatement(checkStmt);
            
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // 자동 커밋 복원
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Connection 해제 오류: " + e.getMessage());
                }
            }
        }
        
        return false;
    }
    
    /**
     * 모든 대여 기록 조회 (조인으로 만화책, 회원 정보 포함)
     */
    public List<Rental> getAllRentals() {
        String sql = """
            SELECT r.id, r.comic_id, r.member_id, r.rental_date, r.return_date,
                   c.title as comic_title, m.name as member_name
            FROM rental r
            JOIN comic c ON r.comic_id = c.id
            JOIN member m ON r.member_id = m.id
            ORDER BY r.id DESC
            """;
        
        List<Rental> rentals = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Rental rental = new Rental();
                rental.setId(rs.getInt("id"));
                rental.setComicId(rs.getInt("comic_id"));
                rental.setMemberId(rs.getInt("member_id"));
                rental.setRentalDate(rs.getString("rental_date"));
                rental.setReturnDate(rs.getString("return_date"));
                rental.setComicTitle(rs.getString("comic_title"));
                rental.setMemberName(rs.getString("member_name"));
                
                rentals.add(rental);
            }
            
        } catch (SQLException e) {
            System.err.println("대여 기록 조회 오류: " + e.getMessage());
        } finally {
            DBUtil.closeAll(conn, stmt, rs);
        }
        
        return rentals;
    }
    
    /**
     * 미반납 대여 기록만 조회
     */
    public List<Rental> getUnreturnedRentals() {
        String sql = """
            SELECT r.id, r.comic_id, r.member_id, r.rental_date, r.return_date,
                   c.title as comic_title, m.name as member_name
            FROM rental r
            JOIN comic c ON r.comic_id = c.id
            JOIN member m ON r.member_id = m.id
            WHERE r.return_date IS NULL
            ORDER BY r.rental_date
            """;
        
        List<Rental> rentals = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Rental rental = new Rental();
                rental.setId(rs.getInt("id"));
                rental.setComicId(rs.getInt("comic_id"));
                rental.setMemberId(rs.getInt("member_id"));
                rental.setRentalDate(rs.getString("rental_date"));
                rental.setReturnDate(rs.getString("return_date"));
                rental.setComicTitle(rs.getString("comic_title"));
                rental.setMemberName(rs.getString("member_name"));
                
                rentals.add(rental);
            }
            
        } catch (SQLException e) {
            System.err.println("미반납 대여 기록 조회 오류: " + e.getMessage());
        } finally {
            DBUtil.closeAll(conn, stmt, rs);
        }
        
        return rentals;
    }
    
    /**
     * 특정 회원의 대여 기록 조회 (선택적 기능)
     */
    public List<Rental> getRentalsByMember(int memberId) {
        String sql = """
            SELECT r.id, r.comic_id, r.member_id, r.rental_date, r.return_date,
                   c.title as comic_title, m.name as member_name
            FROM rental r
            JOIN comic c ON r.comic_id = c.id
            JOIN member m ON r.member_id = m.id
            WHERE r.member_id = ?
            ORDER BY r.rental_date DESC
            """;
        
        List<Rental> rentals = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, memberId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Rental rental = new Rental();
                rental.setId(rs.getInt("id"));
                rental.setComicId(rs.getInt("comic_id"));
                rental.setMemberId(rs.getInt("member_id"));
                rental.setRentalDate(rs.getString("rental_date"));
                rental.setReturnDate(rs.getString("return_date"));
                rental.setComicTitle(rs.getString("comic_title"));
                rental.setMemberName(rs.getString("member_name"));
                
                rentals.add(rental);
            }
            
        } catch (SQLException e) {
            System.err.println("회원별 대여 기록 조회 오류: " + e.getMessage());
        } finally {
            DBUtil.closeAll(conn, stmt, rs);
        }
        
        return rentals;
    }
}