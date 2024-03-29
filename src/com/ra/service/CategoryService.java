package com.ra.service;

import com.ra.entity.model.Category;
import com.ra.entity.model.Product;
import com.ra.service.impl.CategoryServiceImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CategoryService implements CategoryServiceImpl {
    File file;


    public CategoryService() {
        File dataDir = new File("data");
        if(!dataDir.exists()){
            dataDir.mkdir();
        }
        File categoryFile = new File("data/categories.txt");

        if(!categoryFile.exists()){
            try {
                categoryFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.file = categoryFile;
    }
    @Override
    public void saveToFile(List<Category> list) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(list);
        } catch (IOException exception){
            System.err.println("co loi khi them moi");
        }

    }

    @Override
    public List<Category> getAllToFile() {
        List<Category> categories = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            categories = (List<Category>) inputStream.readObject();
        } catch (EOFException e) {

        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    @Override
    public void addCategory() throws Exception {
        Scanner scanner = new Scanner(System.in);
        List<Category> categories = getAllToFile(); // lay danh sach tu file ra
        do {

            // khoi tao doi tuong Category
            try{
                Category category = new Category();
                category.inputData(scanner);
                categories.add(category);
                System.out.println("Thêm danh mục thành công");
                System.out.println("Bạn có muốn nhập tiếp không? ");
                System.out.println("1. Có");
                System.out.println("2. Không");
                int choice = Integer.parseInt(scanner.nextLine());
                if(choice == 2){
                    break;
                }
            }
            catch (Exception e){
                System.out.println("Vui lòng chọn 1 hoặc 2");
            }
        } while (true);

        saveToFile(categories);

    }

    @Override
    public void showListCategory() {
        List<Category> categories = getAllToFile();

        System.out.printf("*------------------------------------------------------------------------------*%n");
        System.out.printf("*-------------------❤️Danh sách danh mục❤️--------------------------------*%n");
        System.out.printf("*------------------------------------------------------------------------------*%n");
        System.out.printf("| %-6s | %-20s | %-25s |%-15s|%n","ID","Tên thể loại","Mô tả","Trạng Thái");
        System.out.printf("|------------------------------------------------------------------------------|%n");
        for (Category category : categories) {
            category.displayData();
        }
        System.out.printf("*------------------------------------------------------------------------------*%n");
    }

    @Override
    public void updateCategory() throws Exception {
        boolean flag = true;
        do{
            try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Nhập vào mã danh mục cần sửa");
            List<Category> categories = getAllToFile();
            for (int i = 0;i < categories.size(); i++) {
                System.out.println((i+1)+". "+categories.get(i).getName());
            }
            int choice = Integer.parseInt(scanner.nextLine());
            if(choice < 1 || choice > categories.size()){
                System.out.println("Vui lòng lựa chọn trong khoảng từ 1 đến "+categories.size());
                return;
            }
            Category category = categories.get(choice-1);
            if(category != null){
                category.displayData();
                category.inputData(scanner);
                saveToFile(categories);
                System.out.println("Sửa danh mục thành công");
                flag = false;
            } else {
                System.out.println("Không tìm thấy danh mục với id là :"+choice);
            }

        } catch (Exception e){
            System.out.println("Vui lòng nhập vào số nguyên dương và không được để trống");
        }}while (flag);


    }

    @Override
    public boolean findAny(Predicate<Category> predicate) {
        List<Category> categories = getAllToFile();
        for(Category b: categories) {
            if(predicate.test(b))
                return true;
        }
        return false;
    }

    @Override
    public Category findByCategoryCodeInListCategory(Integer categoryCode, List<Category> category) {
        for (Category category1 : category) {
            if(category1.getId() == categoryCode){
                return category1;
            }
        }
        return null;
    }

    @Override
    public void findByCategoryNameInListCategory(String categoryName) {
        List<Category> category = getAllToFile();
       int count = 0;
        for (Category category1 : category) {

            if(category1.getName().contains(categoryName)){
                    count ++;
                 category1.displayData();
            }
        }
        if(count == 0){
            System.out.println("Không tìm thấy danh mục nào có tên là :"+categoryName);
        }


    }

    @Override
    public void deleteCategory() {
        boolean flag = true;
      do{
          try {
              Scanner scanner = new Scanner(System.in);
              System.out.println("Nhập vào mã danh mục cần xóa");
              List<Category> categories = getAllToFile();
              for (int i = 0;i < categories.size(); i++) {
                  System.out.println((i+1)+". "+categories.get(i).getName());
              }
              int choice = Integer.parseInt(scanner.nextLine());
              if(choice < 1 || choice > categories.size()){
                  System.out.println("Vui lòng lựa chọn trong khoảng từ  1->"+categories.size());
                  return;
              }
              Integer categoryCodeDelete = categories.get(choice-1).getId();
              Category category = findByCategoryCodeInListCategory(categoryCodeDelete,categories);
              //kiem tra neu trong categories co chua product thi ko xoa
              List<Product> products = new ProductService().getAllToFile();
              // kiem tra xem trong list products co chua category trung voi category.getid ko
              boolean isExist = products.stream().anyMatch(product -> product.getCategory().getId() == category.getId());
              if(category !=null){
                  if(isExist){
                      System.out.println("Không thể xóa danh mục này vì danh mục này đang chứa sản phẩm");
                      return;
                  }else{
                      System.out.println("Bạn có chắc chắn muốn xóa danh mục này không?");
                      System.out.println("1. Có");
                      System.out.println("2. Không");
                      int choice1 = Integer.parseInt(scanner.nextLine());
                      if(choice1 == 2){
                          return;
                      }
                      categories.remove(category);
                      saveToFile(categories);
                      System.out.println("Xóa thành công");
                        flag = false;
                  }
              } else {
                  System.out.println("Rất tiếc không thể tìm thấy danh mục nào nào với mã là "+categoryCodeDelete);
              }
          }catch (Exception e){
              System.out.println("Vui lòng nhập vào số nguyên dương và không được để trống");
            }
      }while (flag);


    }

    @Override
    public void searchCategory() {

    }

    @Override
    public void sortCategory() {

    }
    @Override
    public void statisticCategory() {
        boolean flag = true;
        do{
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Chọn danh mục cần thống kê");
                List<Category> categories = getAllToFile();
                for (int i = 0;i < categories.size(); i++) {
                    System.out.println((i+1)+". "+categories.get(i).getName());
                }
                int choice = Integer.parseInt(scanner.nextLine());
                if(choice < 1 || choice > categories.size()){
                    System.out.println("Vui lòng nhập trong khoảng 1->"+categories.size());
                    return;
                }
                Integer categoryCodeDelete = categories.get(choice-1).getId();
                Category category = findByCategoryCodeInListCategory(categoryCodeDelete,categories);
                List<Product> products = new ProductService().getAllToFile();
                List<Product> statisticListProduct = products.stream().filter(product -> product.getCategory().getId() == category.getId()).collect(Collectors.toList());
                System.out.println("Số lượng sản phẩm thuộc danh mục "+ category.getName()+" là "+statisticListProduct.size() + " sản phẩm");
                for (Product product : statisticListProduct) {
                    product.displayData();
                }
                flag = false;
            }catch (Exception e){
                System.err.println("Vui lòng nhập vào số nguyên dương và không được để trống");
            }
        } while (flag);
    }


}
