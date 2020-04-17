package shb.cloud.employee.entity;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCommon {
  //
  private int resultCode;
  private String resultMsg;
  private String resultDesc;

  public static String toString(ResponseCommon responseCommon) {
    Gson gson = new Gson();
    return gson.toJson(responseCommon);
  }

  public static ResponseCommon sample() {
    return new ResponseCommon(20001, "error message", "관리자에게 문의주세요.");
  }

  public static void main(String[] args) {
    System.out.println(toString(sample()));
  }
}
