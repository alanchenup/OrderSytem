/**
 * 
 */
package com.ordersystem.service;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.ordersystem.common.model.Pic;
import com.ordersystem.common.util.JsonUtil;
import com.ordersystem.dao.DaoPic;

/**
 * @author Administrator
 *
 */
public class PicWrap {
	private static DaoPic daoPic = new DaoPic();

	public static String upload(String picName, BufferedInputStream in) throws Exception {
		Integer id=daoPic.upload(picName,in);		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id.toString());		
		return JsonUtil.toJson(map);
	}
	public static Pic getPic(int id) throws Exception{
		Pic pic = new Pic();
		return daoPic.getPic(id);
		
	}
	/**
     * 缩小或放大图片
     * @param data   图片的byte数据
     * @param w      需要缩到的宽度
     * @param h      需要缩到高度
     * @return       缩放后的图片的byte数据
     */
   public static byte[] changeImgSize(byte[] data, int nw, int nh){
    byte[] newdata = null;
    try{ 
         BufferedImage bis = ImageIO.read(new ByteArrayInputStream(data));
            int w = bis.getWidth();
            int h = bis.getHeight();
            double sx = (double) nw / w;
            double sy = (double) nh / h;
            AffineTransform transform = new AffineTransform();
            transform.setToScale(sx, sy);
            AffineTransformOp ato = new AffineTransformOp(transform, null);
            //原始颜色
            BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
            ato.filter(bis, bid);
           
            //转换成byte字节
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bid, "jpeg", baos);
            newdata = baos.toByteArray();
           
    }catch(IOException e){ 
         e.printStackTrace(); 
    } 
    return newdata;
}

}
