package lk.ijse.Back_end.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CORSFilter", urlPatterns = "/*")
public class CORSFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("Web Filter Called");
        String origin = req.getHeader("Origin");
        System.out.println(origin);
        if(origin.contains("http://127.0.0.1:5500")){
            res.addHeader("Access-Control-Allow-Origin","*");
            res.addHeader("Access-Control-Allow-Methods","GET,POST,DELETE,PUT");
            res.addHeader("Access-Control-Allow-Headers","*");
            res.addHeader("Access-Control-Expose-Headers","Content-Type");
        }
        chain.doFilter(req,res);
    }
}
