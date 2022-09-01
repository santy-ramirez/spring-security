package com.santy.disney.security.responseAndRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestJwt {
   private String username;
   private String password;
}
