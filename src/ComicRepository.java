import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 만화책 데이터 처리 Repository
 * 만화책 관련 모든 DB 작업을 담당
 */
public class ComicRepository {
    
    /**
     * 만화책 등록
     */
    public int addComic(Comic comic) {
        String sql = "INSERT INTO comic (title, volume, author) VALUES (?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, comic.getTitle());
            stmt.setInt(2, comic.getVolume());
            stmt.setString(3, comic.getAuthor());
            
            int result = stmt.executeUpdate();
            
            if (result > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // 생성된 ID 반환
                }
            }
            
        } catch (SQLException e) {
            System.err.println("만화책 등록 오류: " + e.getMessage());
        } finally {
            DBUtil.closeAll(conn, stmt, generatedKeys);
        }
        
        return -1; // 실패시 -1 반환
    }
    
    /**
     * 모든 만화책 목록 조회
     */
    public List<Comic> getAllComics() {
        String sql = "SELECT id, title, volume, author, is_rented, reg_date FROM comic ORDER BY id";
        
        List<Comic> comics = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Comic comic = new Comic();
                comic.setId(rs.getInt("id"));
                comic.setTitle(rs.getString("title"));
                comic.setVolume(rs.getInt("volume"));
                comic.setAuthor(rs.getString("author"));
                comic.setRented(rs.getBoolean("is_rented"));
                comic.setRegDate(rs.getString("reg_date"));
                
                comics.add(comic);
            }
            
        } catch (SQLException e) {
            System.err.println("만화책 목록 조회 오류: " + e.getMessage());
        } finally {
            DBUtil.closeAll(conn, stmt, rs);
        }
        
        return comics;
    }
    
    /**
     * ID로 특정 만화책 조회
     */
    public Comic getComicById(int id) {
        String sql = "SELECT id, title, volume, author, is_rented, reg_date FROM comic WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Comic comic = new Comic();
                comic.setId(rs.getInt("id"));
                comic.setTitle(rs.getString("title"));
                comic.setVolume(rs.getInt("volume"));
                comic.setAuthor(rs.getString("author"));
                comic.setRented(rs.getBoolean("is_rented"));
                comic.setRegDate(rs.getString("reg_date"));
                
                return comic;
            }
            
        } catch (SQLException e) {
            System.err.println("만화책 조회 오류: " + e.getMessage());
        } finally {
            DBUtil.closeAll(conn, stmt, rs);
        }
        
        return null; // 못찾으면 null 반환
    }
    
    /**
     * 만화책 정보 수정
     */
    public boolean updateComic(Comic comic) {
        String sql = "UPDATE comic SET title = ?, volume = ?, author = ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, comic.getTitle());
            stmt.setInt(2, comic.getVolume());
            stmt.setString(3, comic.getAuthor());
            stmt.setInt(4, comic.getId());
            
            int result = stmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("만화책 수정 오류: " + e.getMessage());
        } finally {
            DBUtil.closeAll(conn, stmt);
        }
        
        return false;
    }
    
    /**
     * 만화책 삭제
     */
    public boolean deleteComic(int id) {
        String sql = "DELETE FROM comic WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int result = stmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("만화책 삭제 오류: " + e.getMessage());
        } finally {
            DBUtil.closeAll(conn, stmt);
        }
        
        return false;
    }
    
    /**
     * 만화책 대여 상태 변경
     */
    public boolean updateRentalStatus(int comicId, boolean isRented) {
        String sql = "UPDATE comic SET is_rented = ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setBoolean(1, isRented);
            stmt.setInt(2, comicId);
            
            int result = stmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("만화책 대여 상태 변경 오류: " + e.getMessage());
        } finally {
            DBUtil.closeAll(conn, stmt);
        }
        
        return false;
    }
    
    /**
     * 제목으로 만화책 검색 (선택적 기능)
     */
    public List<Comic> searchComicsByTitle(String keyword) {
        String sql = "SELECT id, title, volume, author, is_rented, reg_date FROM comic WHERE title LIKE ? ORDER BY id";
        
        List<Comic> comics = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Comic comic = new Comic();
                comic.setId(rs.getInt("id"));
                comic.setTitle(rs.getString("title"));
                comic.setVolume(rs.getInt("volume"));
                comic.setAuthor(rs.getString("author"));
                comic.setRented(rs.getBoolean("is_rented"));
                comic.setRegDate(rs.getString("reg_date"));
                
                comics.add(comic);
            }
            
        } catch (SQLException e) {
            System.err.println("만화책 검색 오류: " + e.getMessage());
        } finally {
            DBUtil.closeAll(conn, stmt, rs);
        }
        
        return comics;
    }
}