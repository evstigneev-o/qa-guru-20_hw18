package in.reqres.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SingleUserResponse {
    private Data data;
    private Support support;

    @lombok.Data
    public  class Data {
        private Integer id;
        private String email;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        private String avatar;
    }

    @lombok.Data
    public  class Support {
        private String url;
        private String text;
    }
}
