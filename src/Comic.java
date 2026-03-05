/**
 * 만화책 데이터 클래스
 */
public class Comic {
    private int id;
    private String title;
    private int volume;
    private String author;
    private boolean isRented;
    private String regDate;
    
    // 기본 생성자
    public Comic() {}
    
    // 매개변수 있는 생성자
    public Comic(String title, int volume, String author) {
        this.title = title;
        this.volume = volume;
        this.author = author;
        this.isRented = false;
    }
    
    public Comic(int id, String title, int volume, String author, boolean isRented, String regDate) {
        this.id = id;
        this.title = title;
        this.volume = volume;
        this.author = author;
        this.isRented = isRented;
        this.regDate = regDate;
    }
    
    // Getter들
    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public int getVolume() {
        return volume;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public boolean isRented() {
        return isRented;
    }
    
    public String getRegDate() {
        return regDate;
    }
    
    // Setter들
    public void setId(int id) {
        this.id = id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setVolume(int volume) {
        this.volume = volume;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public void setRented(boolean rented) {
        isRented = rented;
    }
    
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
    
    @Override
    public String toString() {
        return String.format("%d | %-15s | %d권 | %-20s | %s | %s", 
                            id, title, volume, author, 
                            isRented ? "대여중" : "대여가능", regDate);
    }
}