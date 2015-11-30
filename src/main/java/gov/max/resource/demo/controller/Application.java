package gov.max.resource.demo.controller;

import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.RequiresHttpAction;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.oauth.client.CasOAuthWrapperClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class Application {

    @Autowired
    private Config config;

    @Value("${salt}")
    private String salt;

    @RequestMapping("/")
    public String root(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) throws RequiresHttpAction {
        return index(request, response, map);
    }

    @RequestMapping("/index.html")
    public String index(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) throws RequiresHttpAction {
        final WebContext context = new J2EContext(request, response);

        map.put("profile", getStringProfile(context));

        final Clients clients = config.getClients();

        final CasOAuthWrapperClient casOAuthWrapperClient = (CasOAuthWrapperClient) clients.findClient("CasOAuthWrapperClient");
        map.put("urlOauth", casOAuthWrapperClient.getRedirectAction(context, false).getLocation());

        return "index";
    }

    private UserProfile getProfile(WebContext context) {
        final ProfileManager manager = new ProfileManager(context);
        return manager.get(true);
    }

    private String getStringProfile(WebContext context) {
        final UserProfile profile = getProfile(context);
        if (profile == null) {
            return "";
        } else {
            return profile.toString();
        }
    }

    @RequestMapping("/oauth/index.html")
    public String oauth(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        return protectedIndex(request, response, map);
    }

    @RequestMapping("/protected/index.html")
    public String protect(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        return protectedIndex(request, response, map);
    }

    protected String protectedIndex(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        map.put("profile", getStringProfile(context));
        return "protectedIndex";
    }
}
