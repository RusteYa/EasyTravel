package Helpers;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Rustem.
 */
public class RenderHelper {
    private static Configuration cfg;

    private static Configuration getCfg() throws IOException {
        if (cfg == null) {
            cfg = new Configuration(Configuration.VERSION_2_3_27);
            cfg.setDirectoryForTemplateLoading(new File("C:/EasyTravel/web/templates"));
            cfg.setDefaultEncoding("utf-8");
            cfg.setLocale(Locale.US);
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        }
        return cfg;
    }

    public static void render(HttpServletResponse response, Map<String, Object> context, String templateName) {
        try {
            Configuration configuration = getCfg();
            Template template = configuration.getTemplate(templateName);
            template.process(context, response.getWriter());
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}