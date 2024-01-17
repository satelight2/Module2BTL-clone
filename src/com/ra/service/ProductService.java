package com.ra.service;

import com.ra.entity.model.Category;
import com.ra.entity.model.Product;
import com.ra.service.impl.ProductServiceImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductService implements ProductServiceImpl {
    File file;

    public ProductService() {
        File dataDir = new File("data");
        if(!dataDir.exists()){
            dataDir.mkdir();
        }
        File productFile = new File("data/products.txt");

        if(!productFile.exists()){
            try {
                productFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.file = productFile;
    }

    @Override
    public void saveToFile(List<Product> list) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(list);
        } catch (IOException exception){
            System.err.println("co loi khi them moi");
        }
    }

    @Override
    public List<Product> getAllToFile() {
        List<Product> products = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            products = (List<Product>) inputStream.readObject();
        } catch (EOFException e) {

        } catch (IOException e) {
            System.err.println("Loi khi doc file");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public void addProduct() {
        Scanner scanner = new Scanner(System.in);
        List<Product> products = getAllToFile(); // lay danh sach tu file ra
        do {
         try{
             // khoi tao doi tuong Category
             Product product = new Product();
             product.inputData(scanner);
             products.add(product);
             System.out.println("Thêm sản phẩm thành công");
             System.out.println("Bạn có muốn nhập tiếp ko ?");
             System.out.println("1. Có");
             System.out.println("2. Không");
             int choice = Integer.parseInt(scanner.nextLine());
             if(choice == 2){
                 break;
             }
         }catch (Exception e){
             System.out.println("Vui lòng nhập số nguyên dương");
         }
        } while (true);

        saveToFile(products);
    }

    @Override
    public void showProduct() {
        List<Product> products = getAllToFile();
        System.out.printf("*----------------------------------------------------------------------------------------------------------------------------*%n");
        System.out.printf("*-----------------------------------------❤️Danh sách sản  phẩm❤️-------------------------------------------------------*%n");
        System.out.printf("*----------------------------------------------------------------------------------------------------------------------------*%n");
        System.out.printf("| %-6s | %-15s | %-10s|   %-15s|  %-15s|  %-15s|  %-15s|  %-15s| %n",
                "ID","Tên sản phẩm","Giá nhập","Giá bán","Lợi nhuận","Mô tả","Trạng Thái","Danh mục");
        System.out.printf("|----------------------------------------------------------------------------------------------------------------------------|%n");
        for (Product product : products) {
            product.displayData();
        }
        System.out.printf("*----------------------------------------------------------------------------------------------------------------------------*%n");

    }

    @Override
    public void updateProduct() throws Exception {
        boolean flag = true;
        do {
            try{
                System.out.println("Nhap id sản phẩm cần sửa");
                Scanner scanner = new Scanner(System.in);
                String id = scanner.nextLine();
                List<Product> products = getAllToFile();
                boolean isFound = false;
                for (int i = 0; i < products.size(); i++) {
                    if(products.get(i).getId().equals(id)){
                        isFound = true;
                        System.out.println("Tìm thấy sản phẩm với id " + id + " để sửa");
                        products.get(i).displayData();
                        System.out.println("Nhập thông tin mới cho sản phẩm ");
                        System.out.println("Nhập tên mới cho sản phẩm");
                        products.get(i).inputProductName(scanner);
                        System.out.println("Nhập giá nhập mới cho sản phẩm");
                        products.get(i).inputImportPrice(scanner);
                        System.out.println("Nhập giá bán mới cho sản phẩm");
                        products.get(i).inputExportPrice(scanner);
                        System.out.println("Nhập mô tả mới cho sản phẩm");
                        products.get(i).inputDescription(scanner);
                        System.out.println("Nhập trạng thái mới cho sản phẩm");
                        products.get(i).inputStatus(scanner);
                        System.out.println("Chọn danh mục mới cho sản phẩm ");
                        products.get(i).inputCategory(scanner);
                        System.out.println("Sửa thành công");
                        flag = false;
                    }

                }
                if(!isFound){
                    System.out.println("Không tìm thấy sản phẩm với id " + id + " để sửa");
                }
                saveToFile(products);
            }
            catch (Exception e){
                System.out.println("Nhập sai định dạng");
            }
        }while (flag);

    }

    @Override
    public void deleteProduct() {
        System.out.println("Nhâp id sản phẩm cần xóa");
        Scanner scanner = new Scanner(System.in);
        String id = scanner.nextLine();
        List<Product> products = getAllToFile();
        boolean isFound = false;
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getId().equals(id)){
                System.out.println("Bạn có chắc chắn muốn xóa sản phẩm này ko ?");
                System.out.println("1. Có");
                System.out.println("2. Không");
                isFound = true;
                int choice = Integer.parseInt(scanner.nextLine());
                if(choice == 1){
                    products.remove(i);
                    System.out.println("Xóa thành công");
                    break;
                }
            }

        }
        if(!isFound){
            System.out.println("Không tìm thấy sản phẩm với id " + id + " để xóa");
        }
        saveToFile(products);
    }

    @Override
    public void searchProduct() {
        System.out.println("Nhập vào tên hoặc giá sản phẩm");
        //search theo giá trị người dùng nhập nếu là số thì tìm theo giá nếu là chuỗi thì tìm theo tên
        Scanner scanner = new Scanner(System.in);
        String value = scanner.nextLine();
        List<Product> products = getAllToFile();
        boolean isFound = false;
        for (Product product : products) {
            if(product.getProductName().contains(value) || product.getExportPrice() == Double.parseDouble(value)){
                isFound = true;
                product.displayData();
            }
        }
        if(!isFound){
            System.out.println("Không tìm thấy sản phẩm với tên hoặc giá " + value);
        }

    }

    @Override
    public void sortByName() {
        List<Product> products = getAllToFile();
        //tạo ra 1 mảng mới và sắp xếp theo tên
        List<Product> sortedList = new ArrayList<>(products);
        sortedList.sort((o1, o2) -> o1.getProductName().compareTo(o2.getProductName()));
        System.out.printf("| %-6s | %-15s | %-10s|   %-15s|  %-15s|  %-15s|  %-15s|  %-15s| %n",
                "ID","Tên sản phẩm","Giá nhập","Giá bán","Lợi nhuận","Mô tả","Trạng Thái","Danh mục");
        System.out.printf("*----------------------------------------------------------------------------------------------------------------------------*%n");
        for (Product product : sortedList) {
            product.displayData();

        }
        System.out.printf("*----------------------------------------------------------------------------------------------------------------------------*%n");

    }

    @Override
    public void sortByProfit() {
        List<Product> products = getAllToFile();
        //tạo ra 1 mảng mới và sắp xếp theo lợi nhuận
        List<Product> sortedList = new ArrayList<>(products);
        sortedList.sort((o1, o2) -> {
            double profit1 = o1.getExportPrice() - o1.getImportPrice();
            double profit2 = o2.getExportPrice() - o2.getImportPrice();
            if(profit1 > profit2){
                return 1;
            } else if(profit1 < profit2){
                return -1;
            } else {
                return 0;
            }
        });
        System.out.printf("| %-6s | %-15s | %-10s|   %-15s|  %-15s|  %-15s|  %-15s|  %-15s| %n",
                "ID","Tên sản phẩm","Giá nhập","Giá bán","Lợi nhuận","Mô tả","Trạng Thái","Danh mục");
        System.out.printf("*----------------------------------------------------------------------------------------------------------------------------*%n");
        for (Product product : sortedList) {
            product.displayData();
        }
        System.out.printf("*----------------------------------------------------------------------------------------------------------------------------*%n");
    }
}
