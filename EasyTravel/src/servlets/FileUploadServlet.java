package servlets;

import entities.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import repositories.ProfileRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Rustem.
 */
@WebServlet(name = "FileUploadServlet")
public class FileUploadServlet extends HttpServlet {
    private final String UPLOAD_DIRECTORY = "uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getHeader("referer").replace("http://localhost:8081", "");
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
                        String name = new File(item.getName()).getName();
                        item.write(new File(uploadPath + File.separator + name));
                        updateDataBase(path, name, request, uploadPath);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect(path);
    }

    private void updateDataBase(String path, String filename, HttpServletRequest request, String uploadPath) {
        switch (path){
            case "/profilechange":
                User user = (User) request.getSession().getAttribute("current_user");
                user.getProfile().setPhotoPath(uploadPath + filename);
                ProfileRepository.getRepository().updateProfile(user.getProfile());
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
