package com.test.java;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;import java.sql.Timestamp;

   /**
    * u_postcard 实体类
    * Thu Oct 12 14:14:23 CST 2017Lu
    */ 


public class U_postcard{

	/**id*/
	private Long id;

	/**name*/
	private String name;

	/**职称*/
	private String post_title;

	/**公司名称*/
	private String company;

	/**手机号*/
	private String phone;

	/**固话*/
	private String tel;

	/**qq号*/
	private String qq;

	/**用户详细地址*/
	private String address;

	/**frist_word*/
	private String frist_word;

@Id
@Column(name =" id")
	public Long getId(){
		return id;
	}

	public void setId(Long id){
	this.id=id;
	}

@Column(name =" name")
	public String getName(){
		return name;
	}

	public void setName(String name){
	this.name=name;
	}

@Column(name =" post_title")
	public String getPost_title(){
		return post_title;
	}

	public void setPost_title(String post_title){
	this.post_title=post_title;
	}

@Column(name =" company")
	public String getCompany(){
		return company;
	}

	public void setCompany(String company){
	this.company=company;
	}

@Column(name =" phone")
	public String getPhone(){
		return phone;
	}

	public void setPhone(String phone){
	this.phone=phone;
	}

@Column(name =" tel")
	public String getTel(){
		return tel;
	}

	public void setTel(String tel){
	this.tel=tel;
	}

@Column(name =" qq")
	public String getQq(){
		return qq;
	}

	public void setQq(String qq){
	this.qq=qq;
	}

@Column(name =" address")
	public String getAddress(){
		return address;
	}

	public void setAddress(String address){
	this.address=address;
	}

@Column(name =" frist_word")
	public String getFrist_word(){
		return frist_word;
	}

	public void setFrist_word(String frist_word){
	this.frist_word=frist_word;
	}

}

