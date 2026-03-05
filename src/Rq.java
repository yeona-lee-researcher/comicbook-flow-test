import java.util.ArrayList;
import java.util.List;

/**
 * 커맨드 파싱 유틸리티
 * 입력받은 명령어를 파싱하여 명령어와 파라미터로 분리
 */
public class Rq {
    private String command;
    private List<String> params;
    
    public Rq(String input) {
        parseInput(input);
    }
    
    /**
     * 입력 문자열을 파싱하여 명령어와 파라미터 분리
     */
    private void parseInput(String input) {
        String[] parts = input.split("\\s+");
        
        if (parts.length > 0) {
            this.command = parts[0];
            this.params = new ArrayList<>();
            
            for (int i = 1; i < parts.length; i++) {
                params.add(parts[i]);
            }
        }
    }
    
    /**
     * 명령어 반환
     */
    public String getCommand() {
        return command != null ? command : "";
    }
    
    /**
     * 파라미터 개수 반환
     */
    public int getParamCount() {
        return params != null ? params.size() : 0;
    }
    
    /**
     * 특정 인덱스의 파라미터 반환
     */
    public String getParam(int index) {
        if (params != null && index >= 0 && index < params.size()) {
            return params.get(index);
        }
        return null;
    }
    
    /**
     * 특정 인덱스의 파라미터를 정수로 반환
     */
    public Integer getParamAsInt(int index) {
        String param = getParam(index);
        if (param != null) {
            try {
                return Integer.parseInt(param);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
    
    /**
     * 모든 파라미터 반환
     */
    public List<String> getParams() {
        return params != null ? new ArrayList<>(params) : new ArrayList<>();
    }
}