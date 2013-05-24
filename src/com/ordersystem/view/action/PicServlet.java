package com.ordersystem.view.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.sql.BLOB;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ordersystem.common.model.Pic;
import com.ordersystem.service.PicWrap;

public class PicServlet extends HttpServlet {
	File tmpDir = null;// 初始化上传文件的临时存放目录
	File saveDir = null;// 初始化上传文件后的保存目录

	/**
	 * Constructor of the object.
	 */
	public PicServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		response.setContentType("image/jpeg");
		try {
			Pic pic = PicWrap.getPic(id);
			BLOB blob = pic.getPic_blob();
			InputStream inStream = blob.getBinaryStream();
			long nLen = blob.length();
			int nSize = (int) nLen;
			OutputStream toClient = response.getOutputStream();
			byte[] P_Buf = new byte[nSize];
			inStream.read(P_Buf);
			inStream.close();
			byte[] newDate=PicWrap.changeImgSize(P_Buf, 50, 50);
            toClient.write(newDate, 0, newDate.length);
			toClient.flush(); // 强制清出缓冲区
			toClient.close();//

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String res=null;
		
		try {
			if (ServletFileUpload.isMultipartContent(request)) {
				// DiskFileItemFactory dff = new DiskFileItemFactory();//创建该对象
				// dff.setRepository(tmpDir);//指定上传文件的临时目录
				// dff.setSizeThreshold(1024000);//指定在内存中缓存数据大小,单位为byte
				// ServletFileUpload sfu = new ServletFileUpload(dff);//创建该对象
				// sfu.setFileSizeMax(5000000);//指定单个上传文件的最大尺寸
				// sfu.setSizeMax(10000000);//指定一次上传多个文件的总尺寸
				// FileItemIterator fii =
				// sfu.getItemIterator(request);//解析request
				// 请求,并返回FileItemIterator集合

				String imagename = null;
				String value = null;
				// Create a factory for disk-based file items
				DiskFileItemFactory factory = new DiskFileItemFactory();

				// Configure a repository (to ensure a secure temp location is
				// used)
				ServletContext servletContext = this.getServletConfig()
						.getServletContext();
				File repository = (File) servletContext
						.getAttribute("javax.servlet.context.tempdir");
				factory.setRepository(repository);

				// Create a new file upload handler
				ServletFileUpload upload = new ServletFileUpload(factory);

				// Parse the request
				// FileItemIterator items= upload.getItemIterator(request);
				// Parse the request
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> iter = items.iterator();

				while (iter.hasNext()) {
					FileItem fis = iter.next();// 从集合中获得一个文件流
					if (!fis.isFormField() && fis.getName().length() > 0) {// 过滤掉表单中非文件域
						String fileName = fis.getName();// 获得上传文件的文件名
						BufferedInputStream in = new BufferedInputStream(
								fis.getInputStream());// 获得文件输入流
						

						// BufferedOutputStream out = new
						// BufferedOutputStream(new FileOutputStream(new
						// File(saveDir+"\\"+fileName)));//获得文件输出流
						// Streams.copy(in, out, true);//开始把文件写到你指定的上传文件夹
						 res=PicWrap.upload(fileName, in);
					} else if (fis.isFormField()) {
						imagename = fis.getFieldName();
						value = fis.getString();

					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
        OutputStream out = response.getOutputStream();
        out.write(res.getBytes("UTF-8"));
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		/*
		 * 对上传文件夹和临时文件夹进行初始化
		 */
		super.init();
		String tmpPath = "c:\\tmpdir";
		String savePath = "c:\\updir";
		tmpDir = new File(tmpPath);
		saveDir = new File(savePath);
		if (!tmpDir.isDirectory())
			tmpDir.mkdir();
		if (!saveDir.isDirectory())
			saveDir.mkdir();
	}

}
