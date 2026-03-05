import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 회원 데이터 처리 Repository
 * 회원 관련 모든 DB 작업을 담당
 */
public class MemberRepository {
    
    /**
     * 회원 등록
     */
    public int addMember(Member member) {
        String sql = "INSERT INTO member (name, phone) VALUES (?, ?)";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getPhone());
            
            int result = stmt.executeUpdate();
            
            if (result > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // 생성된 ID 반환
                }
            }
            
        } catch (SQLException e) {
            System.err.println("회원 등록 오류: " + e.getMessage());
        } finally {
            DBUtil.closeAll(conn, stmt, generatedKeys);
        }
        
        return -1; // 실패시 -1 반환
    }
    
    /**
     * 모든 회원 목록 조회
     */
    public List<Member> getAllMembers() {
        String sql = "SELECT id, name, phone, reg_date FROM member ORDER BY id";
        
        List<Member> members = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                member.setPhone(rs.getString("phone"));
                member.setRegDate(rs.getString("reg_date"));
                
                members.add(member);
            }
            
        } catch (SQLException e) {
            System.err.println("회원 목록 조회 오류: " + e.getMessage());
        } finally {
            DBUtil.closeAll(conn, stmt, rs);
        }
        
        return members;
    }
    
    /**
     * ID로 특정 회원 조회
     */
    public Member getMemberById(int id) {
        String sql = "SELECT id, name, phone, reg_date FROM member WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                member.setPhone(rs.getString("phone"));
                member.setRegDate(rs.getString("reg_date"));
                
                return member;
            }
            
        } catch (SQLException e) {
            System.err.println("회원 조회 오류: " + e.getMessage());
        } finally {
            DBUtil.closeAll(conn, stmt, rs);
        }
        
        return null; // 못찾으면 null 반환
    }
    
    /**
     * 회원 정보 수정
     */
    public boolean updateMember(Member member) {
        String sql = "UPDATE member SET name = ?, phone = ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getPhone());
            stmt.setInt(3, member.getId());
            
            int result = stmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("회원 수정 오류: " + e.getMessage());
        } finally {
            DBUtil.closeAll(conn, stmt);
        }
        
        return false;
    }
    
    /**
     * 회원 삭제
     */
    public boolean deleteMember(int id) {
        String sql = "DELETE FROM member WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int result = stmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("회원 삭제 오류: " + e.getMessage());
        } finally {
            DBUtil.closeAll(conn, stmt);
        }
        
        return false;
    }
    
    /**
     * 이름으로 회원 검색 (선택적 기능)
     */
    public List<Member> searchMembersByName(String keyword) {
        String sql = "SELECT id, name, phone, reg_date FROM member WHERE name LIKE ? ORDER BY id";
        
        List<Member> members = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                member.setPhone(rs.getString("phone"));
                member.setRegDate(rs.getString("reg_date"));
                
                members.add(member);
            }
            
        } catch (SQLException e) {
            System.err.println("회원 검색 오류: " + e.getMessage());
        } finally {
            DBUtil.closeAll(conn, stmt, rs);
        }
        
        return members;
    }
}