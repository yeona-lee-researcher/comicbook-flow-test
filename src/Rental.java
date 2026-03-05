/**
 * 대여 기록 데이터 클래스
 */
public class Rental {
    private int id;
    private int comicId;
    private int memberId;
    private String rentalDate;
    private String returnDate;
    
    // 조인 정보를 위한 추가 필드
    private String comicTitle;
    private String memberName;
    
    // 기본 생성자
    public Rental() {}
    
    // 매개변수 있는 생성자
    public Rental(int comicId, int memberId) {
        this.comicId = comicId;
        this.memberId = memberId;
    }
    
    public Rental(int id, int comicId, int memberId, String rentalDate, String returnDate) {
        this.id = id;
        this.comicId = comicId;
        this.memberId = memberId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }
    
    // Getter들
    public int getId() {
        return id;
    }
    
    public int getComicId() {
        return comicId;
    }
    
    public int getMemberId() {
        return memberId;
    }
    
    public String getRentalDate() {
        return rentalDate;
    }
    
    public String getReturnDate() {
        return returnDate;
    }
    
    public String getComicTitle() {
        return comicTitle;
    }
    
    public String getMemberName() {
        return memberName;
    }
    
    // Setter들
    public void setId(int id) {
        this.id = id;
    }
    
    public void setComicId(int comicId) {
        this.comicId = comicId;
    }
    
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    
    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }
    
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    
    public void setComicTitle(String comicTitle) {
        this.comicTitle = comicTitle;
    }
    
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    
    /**
     * 반납 여부 확인
     */
    public boolean isReturned() {
        return returnDate != null && !returnDate.isEmpty();
    }
    
    @Override
    public String toString() {
        String comic = comicTitle != null ? comicTitle : "ID:" + comicId;
        String member = memberName != null ? memberName : "ID:" + memberId;
        String returnInfo = isReturned() ? returnDate : "-";
        
        return String.format("%d | %-15s | %-10s | %s | %s", 
                            id, comic, member, rentalDate, returnInfo);
    }
}