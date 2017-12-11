package com.javalec.ex;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

/**
 * Servlet implementation class ex22
 */
@WebServlet("/ex22")
public class ex22 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ex22() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


		int size = 1024 * 1024 * 10; //10M 파일 size 제한
		
		String path = request.getRealPath("ex22_fileFolder");
		
		String file = "";
		String oriFile = "";
		
		try{
			
			MultipartParser mp = new MultipartParser(request, 1000000, true, true, "EUC-KR");

				Part part = null;
				while ((part = mp.readNextPart()) != null) {
					String name = part.getName();
					if (part.isParam()) {
						// it's a parameter part
						ParamPart paramPart = (ParamPart) part;
						String value = paramPart.getStringValue();

						System.out.println("param; name=" + name + ", value=" + value);
					} else if (part.isFile()) {
						// it's a file part
						FilePart filePart = (FilePart) part;
						String fileName = filePart.getFileName();
						if (fileName != null) {
							// the part actually contained a file
							// StringWriter sw = new StringWriter();
							// long size = filePart.writeTo(new File(System
							// .getProperty("java.io.tmpdir")));
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							long filesize = filePart.writeTo(baos);
							System.out.println("file; name=" + name + "; filename=" + fileName
									+ ", filePath=" + filePart.getFilePath()
									+ ", content type=" + filePart.getContentType()
									+ ", size=" + filesize);
							
							

						          filePart.setRenamePolicy(new DefaultFileRenamePolicy());  // null policy is OK
						          // The part actually contained a file
						          File dir = new File(path);
						          filePart.writeTo(dir);
						        
						     
						} else {
							// the field did not contain a file
							System.out.println("file; name=" + name + "; EMPTY");
						}
						
					}
				}

				
			MultipartRequest multi = new MultipartRequest(request, path, size, "EUC-KR", new DefaultFileRenamePolicy());
			

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
