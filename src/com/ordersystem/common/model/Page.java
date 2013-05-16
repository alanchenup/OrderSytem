/**
 * 
 */
package com.ordersystem.common.model;

/**
 * @author Administrator
 *
 */
public class Page {

	private int pagesize;
	//当前页面大小,处理不足一页的情况
	private int c_pagesize;
	private int pageindex;
	private int pageall;
	private int sum;
	private boolean hasNext=true;
	private boolean hasPre=true;
	
	public Page(int pagesize,int pageindex,int sum){
		this.pagesize=pagesize;
		this.pageindex=pageindex;
		
		this.sum=sum;
		if(sum%this.pagesize!=0){
			this.pageall=sum/pagesize + 1;
			}else{
				this.pageall=sum/pagesize;
			}
		if(pageindex==1){
			this.hasPre=false;
		}	
		if(pageindex==this.pageall){
			this.hasNext=false;
			this.c_pagesize=sum-(this.pageindex-1)*this.pagesize;
		}else{
			this.c_pagesize=pagesize;
		}
		
	}

	public int getC_pagesize() {
		return c_pagesize;
	}

	public void setC_pagesize(int c_pagesize) {
		this.c_pagesize = c_pagesize;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getPageindex() {
		return pageindex;
	}

	public void setPageindex(int pageindex) {
		this.pageindex = pageindex;
	}

	public int getPageall() {
		return pageall;
	}

	public void setPageall(int pageall) {
		this.pageall = pageall;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
		

	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean isHasPre() {
		return hasPre;
	}

	public void setHasPre(boolean hasPre) {
		this.hasPre = hasPre;
	}
	
	
	
	
	
	
}
