package org.zerock.knock.service.layerClass;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.zerock.knock.dto.Enum.SocialLoginType;
import org.zerock.knock.repository.user.SSOUserRepository;
import org.zerock.knock.service.oAuth.SocialOauth;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final List<SocialOauth> socialOauthList;
    private final HttpServletResponse response;
    private static final Logger logger = LoggerFactory.getLogger(OauthService.class);

    public void request(SocialLoginType socialLoginType)
    {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        String redirectURL = socialOauth.getOauthRedirectURL();

        try
        {
            response.sendRedirect(redirectURL);
        }
        catch (IOException e)
        {
            logger.debug(e.getMessage());
        }
    }

    public void requestAccessToken(SocialLoginType socialLoginType, String code) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);

        String callBackResponse = socialOauth.requestAccessToken(code);
        ObjectMapper mapper = new ObjectMapper();

        // JSON 파싱
        JsonNode jsonNode = null;
        try
        {
            jsonNode = mapper.readTree(callBackResponse);
        }
        catch (JsonProcessingException e)
        {
            logger.debug(e.getMessage());
        }

        // access_token 추출
        assert jsonNode != null;
        String accessToken = jsonNode.get("access_token").asText();

        socialOauth.requestUserInfo(accessToken);

    }

    private SocialOauth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }

}
