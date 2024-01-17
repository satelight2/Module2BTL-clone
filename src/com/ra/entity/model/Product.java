package com.ra.entity.model;

import com.ra.entity.impl.IProduct;
import com.ra.service.CategoryService;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class Product implements IProduct, Serializable {
    private String id;
    private String productName;
    private double importPrice;
    private double exportPrice;
    private double profit;
    private String description;
    private boolean status;
//    private int categoryId;
    private Category category;
//    private String categoryName;

    public Product() {
    }

    public Product(String id, String productName, double importPrice, double exportPrice, double profit, String description, boolean status, int categoryId, Category category ) {
        this.id = id;
        this.productName = productName;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.profit = profit;
        this.description = description;
        this.status = status;
//        this.categoryId = categoryId;
        this.category = category;
//        this.categoryName = categoryName;
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public double getExportPrice() {
        return exportPrice;
    }

    public double getProfit() {
        return profit;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStatus() {
        return status;
    }

//    public int getCategoryId() {
//        return categoryId;
//    }

        public Category getCategory() {
            return category;
        }
//    public String getCategoryName() {
//        return categoryName;
//    }

    public void setId(String id) throws Exception {
        if(id == null || id.length() < 4 || id.length() > 30)
            throw new Exception("Id phải từ 6 - 30 ký tự");
        if(!id.startsWith("P"))
            throw new Exception("Id phải bắt đầu bằng P");
        this.id = id;
    }

    public void setProductName(String productName) throws Exception {
        if(productName == null || productName.length() < 6 || productName.length() > 30)
            throw new Exception("Tên sản phẩm phải từ 6 - 30 ký tự");
        this.productName = productName;
    }

    public void setImportPrice(double importPrice) throws Exception {
            if(exportPrice < 0)
                throw new Exception("Giá nhập phải lớn hơn 0" );
        this.importPrice = importPrice;
    }

    public void setExportPrice(double exportPrice) throws Exception {
        if(exportPrice < 0)
            throw new Exception("Giá bán phải lớn hơn 0");
        if(exportPrice < importPrice*(1+MIN_INTEREST_RATE))
            throw new Exception("Giá bán phải lớn hơn giá nhập ít nhất "+MIN_INTEREST_RATE+" lần" );
        this.exportPrice = exportPrice;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public void setDescription(String description) throws Exception {
        if(description == null || description.isEmpty() )
            throw new Exception("Mô tả không được để trống");
        this.description = description;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

//    public void setCategoryId(int categoryId) {
//        this.categoryId = categoryId;
//    }
//
    public void setCategory(Category category) {
        this.category = category;
    }
//    public void setCategoryName(String categoryName) {
//        this.categoryName = categoryName;
//    }

    @Override
    public void inputData(Scanner sc) {
        boolean flag = true;
        do {
            try{
                System.out.println("Nhập mã sản phẩm: ");
                setId(sc.nextLine());
                flag = false;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                flag = true;
            }
        }while (flag);
        System.out.println("Nhập tên sản phẩm: ");
        inputProductName(sc);
        System.out.println("Nhập vào giá nhập : ");
        inputImportPrice(sc);
        System.out.println("Nhập vào giá bán : ");
        inputExportPrice(sc);
        System.out.println("Nhập vào mô tả : ");
        inputDescription(sc);
        System.out.println("Nhập vào trạng thái : ");
        inputStatus(sc);
        System.out.println("Nhập vào danh mục : ");
        inputCategory(sc);

//        do {
//            try{
//                System.out.println("Nhập tên sản phẩm: ");
//                setProductName(sc.nextLine());
//                flag = false;
//            }
//            catch (Exception e){
//                System.out.println(e.getMessage());
//                flag = true;
//            }
//        }while (flag);

//        do {
//            try{
//                System.out.println("Nhập vào giá nhập : ");
//                setImportPrice(Double.parseDouble(sc.nextLine()));
//                flag = false;
//            }
//            catch (Exception e){
//                System.out.println(e.getMessage());
//                flag = true;
//            }
//        }while (flag);
//
//        do {
//            try{
//                System.out.println("Nhập vào giá bán : ");
//                setExportPrice(Double.parseDouble(sc.nextLine()));
//                flag = false;
//            }
//            catch (Exception e){
//                System.out.println(e.getMessage());
//                flag = true;
//            }
//        }while (flag);
//
//        do {
//            try{
//                System.out.println("Nhập vào mô tả : ");
//                setDescription(sc.nextLine());
//                flag = false;
//            }
//            catch (Exception e){
//                System.out.println(e.getMessage());
//                flag = true;
//            }
//        }while (flag);
//
//        do {
//            try{
//                System.out.println("Nhập trạng thái: ");
//                String str = sc.nextLine();
//                setStatus(Boolean.parseBoolean(str));
//                if (!str.equalsIgnoreCase("true") && !str.equalsIgnoreCase("false")) {
//                    throw new Exception("Trạng thái chỉ nhận giá trị 'true' hoặc 'false'");
//                }
//
//
//                flag = false;
//            }
//            catch (Exception e){
//                System.out.println(e.getMessage());
//                flag = true;
//            }
//        }while (flag);
//
//        do {
//            System.out.println("Chọn  mã danh mục: ");
//            try{
//                CategoryService categoryService = new CategoryService();
//                List<Category> categories = categoryService.getAllToFile();
//                   for (int i = 0;i < categories.size(); i++) {
//                       System.out.println((i+1)+". "+categories.get(i).getName());
//                   }
//                   int choice = Integer.parseInt(sc.nextLine());
//                     if(choice < 1 || choice > categories.size())
//                          throw new Exception("Chỉ được chọn từ 1 - "+categories.size());
//                        setCategory(categories.get(choice-1));
////                        setCategoryName(categories.get(choice-1).getName());
//                flag = false;
//            }
//            catch (Exception e){
//                System.out.println(e.getMessage());
//                flag = true;
//            }
//        }while (flag);

    }
    public void inputProductName(Scanner sc){
        boolean flag = true;
        do {
            try{
                setProductName(sc.nextLine());
                flag = false;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                flag = true;
            }
        }while (flag);

    }
    public void inputImportPrice(Scanner sc){
        boolean flag = true;
        do {
            try{
                setImportPrice(Double.parseDouble(sc.nextLine()));
                flag = false;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                flag = true;
            }
        }while (flag);

    }
    public void inputExportPrice(Scanner sc){
        boolean flag = true;
        do {
            try{
                setExportPrice(Double.parseDouble(sc.nextLine()));
                flag = false;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                flag = true;
            }
        }while (flag);

    }
    public void inputDescription(Scanner sc){
        boolean flag = true;
        do {
            try{
                setDescription(sc.nextLine());
                flag = false;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                flag = true;
            }
        }while (flag);

    }
    public void inputStatus(Scanner sc){
        boolean flag = true;
        do {
            try{
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

    }
    public void inputCategory(Scanner sc){
        boolean flag = true;
        do {
            try{
                CategoryService categoryService = new CategoryService();
                List<Category> categories = categoryService.getAllToFile();
                   for (int i = 0;i < categories.size(); i++) {
                       System.out.println((i+1)+". "+categories.get(i).getName());
                   }
                   int choice = Integer.parseInt(sc.nextLine());
                     if(choice < 1 || choice > categories.size())
                          throw new Exception("Chỉ được chọn từ 1 - "+categories.size());
                        setCategory(categories.get(choice-1));

                flag = false;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                flag = true;
            }
        }while (flag);

    }


    @Override
    public void displayData() {
        calProfit();
        String statusString = isStatus() ? "Còn hàng" : "Ngừng kinh doanh";
        System.out.printf("| %-6s | %-15s | %-10s| %-15s  |%-15s  |%-15s  |%-15s  |%-15s  | %n",this.id,this.productName,this.importPrice,this.exportPrice,this.profit,this.description,statusString,this.category.getName());


    }

    @Override
    public void calProfit() {

        this.profit = this.exportPrice - this.importPrice;

    }
}
