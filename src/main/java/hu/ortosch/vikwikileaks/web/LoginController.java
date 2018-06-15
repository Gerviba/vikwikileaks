package hu.ortosch.vikwikileaks.web;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import hu.gerviba.authsch.AuthSchAPI;
import hu.gerviba.authsch.response.AuthResponse;
import hu.gerviba.authsch.response.ProfileDataResponse;
import hu.gerviba.authsch.struct.BMEUnitScope;
import hu.gerviba.authsch.struct.Scope;
import hu.ortosch.vikwikileaks.model.UserEntity;
import hu.ortosch.vikwikileaks.services.BanService;
import hu.ortosch.vikwikileaks.services.SubjectService;

@Controller
public class LoginController {

    @Autowired
    private AuthSchAPI authSch;
    
    @Autowired
    SubjectService subjects;
    
    @Autowired
    ControllerUtil util;
    
    @Autowired
    BanService bans;
    
    @Value("${salt.tier1:demosalt1}")
    private String saltTier1;
    
    @Value("${salt.name:demosalt2}")
    private String saltName;
    
    @Value("${salt.ownership:demosalt3}")
    private String saltOwnership;

    @GetMapping("/loggedin")
    public String loggedIn(@RequestParam String code, @RequestParam String state, HttpServletRequest request) {
        if (!buildUniqueState(request).equals(state))
            return "redirect/?invalid-state";
        
        Authentication auth = null;
        try {
            AuthResponse response = authSch.validateAuthentication(code);
            ProfileDataResponse profile = authSch.getProfile(response.getAccessToken());
            
            if (profile.getBmeUnitScopes().contains(BMEUnitScope.BME_VIK_ACTIVE)) {
                String uuid = profile.getInternalId().toString();
                String banMessage = bans.getBanMessage(uuid);
                if (banMessage != null) {
                    System.out.println("INVALID LOGIN FOR " + uuid + " BAN MESSAGE: " + banMessage);
                    return "redirect:/?ban=" + UriUtils.encodeQueryParam(banMessage, Charset.defaultCharset());
                }
                
                String tier1 = util.sha1(uuid + saltTier1);
                UserEntity user = new UserEntity(
                        util.sha1(tier1 + saltName),
                        util.sha1(tier1 + saltOwnership), 
                        profile.getCourses());
                
                subjects.checkIfExists(user.getCourses());
                auth = new UsernamePasswordAuthenticationToken(code, state, getAuthorities(user));
                request.getSession().setAttribute("user", user);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                return "redirect:/courses/?not-allowed";
            }
            
        } catch (Exception e) {
            if(auth != null)
                auth.setAuthenticated(false);
            e.printStackTrace();
        }
        
        return (auth != null && auth.isAuthenticated()) ? "redirect:/courses/" : "redirect:/courses/?error";
    }

    private List<GrantedAuthority> getAuthorities(UserEntity user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        if (user.isSysadmin()) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        }

        return authorities;
    }

    @GetMapping("/login")
    public String items(HttpServletRequest request) {
        return "redirect:" + authSch.generateLoginUrl(buildUniqueState(request),
                Scope.BASIC, Scope.BME_UNIT_SCOPE, Scope.COURSES);
    }

    static String buildUniqueState(HttpServletRequest request) {
        return hashString(request.getSession().getId()
                + request.getLocalAddr()
                + request.getLocalPort());
    }

    static final String hashString(String in) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return String.format("%064x", new BigInteger(1, digest.digest(in.getBytes())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
    	request.removeAttribute("user");
    	request.getSession().removeAttribute("user");
    	request.changeSessionId();
    	return "redirect:/?logged-out";
    }
    
}
