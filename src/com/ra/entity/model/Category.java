package com.ra.entity.model;

import com.ra.entity.impl.ICategory;
import com.ra.service.CategoryService;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class Category implements ICategory, Serializable {
    private static int AUTO_ID = 0;
    private int id;
    private String name;
    private String description;
    private boolean status;

    public Category() {
    }
    public Category(int id, String name, String description, boolean status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setId(int id) throws Exception{
        CategoryService categoryService = new CategoryService();
        if(id <= 0)
            throw new Exception("Id phải lớn hơn 0");
        if(categoryService.findAny(category -> category.getId() == id))
            throw new Exception("Id đã tồn tại");
        this.id = id;
    }

    public void setName(String name) throws Exception{
        CategoryService categoryService = new CategoryService();
        if(name == null || name.length() < 6 || name.length() >30 )
            throw new Exception( "Tên phải từ 6 - 30 ký tự");
        if(this.id == 0){
            if(categoryService.findAny(category -> category.getName().equals(name)))
                throw new Exception("Tên danh mục đã tồn tại");
        }else{
            if(categoryService.findAny(category -> category.getName().equals(name) && category.getId() != this.id))
                throw new Exception("Tên danh mục đã tồn tại");
        }

        this.name = name;
    }
    public void setDescription(String description) throws Exception {
        if(description == null || description.length() <=0)
            throw new Exception( "Mô tả không được để trống");
        this.description = description;
    }
    public void setStatus(boolean status)   {
        this.status = status;
    }
    @Override
    public void inputData(Scanner sc) throws Exception {
        boolean flag = true;
        do {
            try{
                System.out.println("Nhập tên danh mục: ");
                setName(sc.nextLine());
                flag = false;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                flag = true;
            }
        }while (flag);
        do {
            try{
                System.out.println("Nhập mô tả: ");
                setDescription(sc.nextLine());
                flag = false;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                flag = true;
            }
        }while (flag);
        do {
            try{
                System.out.println("Nhập trạng thái: ");
                String str = sc.nextLine();
                setStatus(Boolean.parseBoolean(str));
                if (!str.equalsIgnoreCase("true") && !str.equalsIgnoreCase("false")) {
                    throw new Exception("Trạng thái chỉ nhận giá trị 'true' hoặc 'false'");
                }


                flag = false;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                flag = true;
            }
        }while (flag);

        if(this.id==0)
             id = AUTO_ID++;

    }

    @Override
    public void displayData() {
        String statusString = isStatus() ? "Hoạt động" : "Không hoạt động";
        System.out.printf("| %-6d | %-20s | %-25s| %-15s  |%n", getId(),getName(),getDescription(),statusString);
    }
}
