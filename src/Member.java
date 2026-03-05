/**
 * 회원 데이터 클래스
 */
public class Member {
    private int id;
    private String name;
    private String phone;
    private String regDate;
    
    // 기본 생성자
    public Member() {}
    
    // 매개변수 있는 생성자
    public Member(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
    
    public Member(int id, String name, String phone, String regDate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.regDate = regDate;
    }
    
    // Getter들
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public String getRegDate() {
        return regDate;
    }
    
    // Setter들
    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
    
    @Override
    public String toString() {
        return String.format("%d | %-10s | %-15s | %s", 
                            id, name, phone != null ? phone : "-", regDate);
    }
}