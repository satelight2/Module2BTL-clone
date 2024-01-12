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
        System.out.println("Nhap id san pham can sua");
        Scanner scanner = new Scanner(System.in);
        String id = scanner.nextLine();
        List<Product> products = getAllToFile();
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getId().equals(id)){
                System.out.println("Nhap thong tin moi");
                System.out.println("Nhap ten san pham");
                products.get(i).setProductName(scanner.nextLine());
                System.out.println("Nhap gia san pham");
                products.get(i).setImportPrice(Double.parseDouble(scanner.nextLine()));
                System.out.println("Nhap gia ban");
                products.get(i).setExportPrice(Double.parseDouble(scanner.nextLine()));
                System.out.println("Nhap mo ta");
                products.get(i).setDescription(scanner.nextLine());
                System.out.println("Nhap trang thai");
                products.get(i).setStatus(Boolean.parseBoolean(scanner.nextLine()));
                System.out.println("Chon danh muc san pham");
                CategoryService categoryService = new CategoryService();
                List<Category> categories = categoryService.getAllToFile();
                for (int j = 0;j < categories.size(); j++) {
                    System.out.println((j+1)+". "+categories.get(j).getName());
                }
                int choice = Integer.parseInt(scanner.nextLine());
                if(choice < 1 || choice > categories.size())
                    throw new Exception("Chỉ được chọn từ 1 - "+categories.size());
                products.get(i).setCategoryId(categories.get(choice-1).getId());
            }
            System.out.println("Không tìm thấy sản phẩm với id " + id + " để sửa");

        }
        saveToFile(products);

    }

    @Override
    public void deleteProduct() {
        System.out.println("Nhâp id sản phẩm cần xóa");
        Scanner scanner = new Scanner(System.in);
        String id = scanner.nextLine();
        List<Product> products = getAllToFile();
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getId().equals(id)){
                System.out.println("Bạn có chắc chắn muốn xóa sản phẩm này ko ?");
                System.out.println("1. Có");
                System.out.println("2. Không");

                int choice = Integer.parseInt(scanner.nextLine());
                if(choice == 1){
                    products.remove(i);
                    System.out.println("Xóa thành công");
                    break;
                }

            }
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
        for (Product product : products) {
            if(product.getProductName().contains(value) || product.getExportPrice() == Double.parseDouble(value)){
                product.displayData();
            }
            System.out.println("Không tìm thấy sản phẩm với id " + value + " để xóa");
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
