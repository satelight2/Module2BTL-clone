package com.ra.view;

import com.ra.service.CategoryService;
import com.ra.service.ProductService;

import java.util.Scanner;

public class Menu {
    private static CategoryService categoryService = new CategoryService();
    private static ProductService productService = new ProductService();
    private static final Scanner scanner = new Scanner(System.in);
    public static void showMenu() throws Exception {

        do {
            System.out.println("==========QUẢN LÝ KHO==========");
            System.out.println("1. Quản lý danh mục ");
            System.out.println("2. Quản lý sản phẩm");
            System.out.println("3. Thoát");
            System.out.println("Mời bạn chọn từ 1 - 3");
            System.out.print("Lựa chọn của bạn là: ");

            try{
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice){
                    case 1:
                        menuCategory();
                        break;
                    case 2:
                        menuProduct();
                        break;
                    case 3:
                        System.exit(0);
                    default:
                        System.out.println("Chọn sai mời chọn lại");
                }
            }catch (NumberFormatException e){
                System.err.println("Lựa chọn phải là số nguyên từ 1 - 3");
            }
        } while (true);

    }
    public static void menuCategory() throws Exception {
        boolean check = true;
        do {
            System.out.println("======== QUẢN LÝ DANH MỤC=========");
            System.out.println("1. Thêm mới danh mục");
            System.out.println("2. Cập nhật danh mục");
            System.out.println("3. Xem danh mục");
            System.out.println("4. Xóa danh mục");
            System.out.println("5. Tìm kiếm danh mục theo tên danh mục");
            System.out.println("6. Thống kê số lượng sp đang có trong danh mục");
            System.out.println("7. Quay lại");
            System.out.println("Mời bạn chọn từ 1 - 6");
            System.out.print("Lựa chọn của bạn là: ");

            try{
                int choice1 = Integer.parseInt(scanner.nextLine());
                switch (choice1){
                    case 1:
                        System.out.println("Thực hiện thêm mới");
                        categoryService.addCategory();
                        break;
                    case 2:
                        System.out.println("Thực hiện cập nhật");
                        categoryService.updateCategory();
                        break;
                    case 3:
                        System.out.println("Thực hiện xem");
                        categoryService.showListCategory();
                        break;
                    case 4:
                        System.out.println("Thực hiện xóa");
                        categoryService.deleteCategory();
                        break;
                    case 5:
                        System.out.println("Thực hiện tìm kiếm");
                        System.out.println("Nhập tên danh mục cần tìm kiếm: ");
                        String name = scanner.nextLine();
                        categoryService.findByCategoryNameInListCategory(name);
                        break;
//                    do {
//                        System.out.println("Nhập vào ID muốn cập nhật: ");
//                        String idUpdate = sc.nextLine();
//                        Product updateProduct = productService.searchProductById(idUpdate);
//                        if(updateProduct == null){
//                            System.err.printf("Sản phẩm có ID là %s không tồn tại.\n",idUpdate);
//                        } else {
//                            productService.update(sc,updateProduct);
//                        }
//                        System.out.print("Bạn có muốn tiếp tục cập nhật sản phẩm khác không (Y/N):");
//                        selection = sc.nextLine();
//                    } while (selection.equalsIgnoreCase("Y"));
//                    break;
                    case 6:
                        System.out.println("Thực hiện thống kê");
                        categoryService.statisticCategory();
                        break;
                    case 7:
                        check = false;
                        break;
                    default:
                        System.out.println("Chọn sai mời chọn lại");
                }
            }catch (NumberFormatException e){
                System.err.println("Lựa chọn phải là số nguyên từ 1 - 6");

            }
        } while (check);
    }
    public static void menuProduct() throws Exception {
        boolean check = true;
        do {
            System.out.println("========Quản lý Sản phẩm=========");
            System.out.println("1. Thêm mới Sản phẩm");
            System.out.println("2. Xem danh sách Sản phẩm");
            System.out.println("3. Cập nhật Sản phẩm");
            System.out.println("4. Xóa Sản phẩm");
            System.out.println("5. Tìm kiếm Sản phẩm");
            System.out.println("6. Hiển thị sản phẩm theo tên A-Z");
            System.out.println("7. Hiển thị sản phẩm theo lợi nhuận từ cao-thấp");
            System.out.println("8. Quay lại");
            System.out.println("Mời bạn chọn từ 1 - 5");
            System.out.print("Lựa chọn của bạn là: ");

            try{
                int choice1 = Integer.parseInt(scanner.nextLine());
                switch (choice1){
                    case 1:
                        System.out.println("Thực hiện thêm mới Sản phẩm");
                        productService.addProduct();
                        break;
                    case 2:
                        System.out.println("Xem danh sách Sản phẩm");
                        productService.showProduct();
                        break;
                    case 3:
                        System.out.println("Thực hiện cập nhật Sản phẩm");
                        productService.updateProduct();
                        break;
                    case 4:
                        System.out.println("Thực hiện xóa Sản phẩm");
                        productService.deleteProduct();
                        break;
                    case 5:
                        System.out.println("Thực hiện tìm kiếm Sản phẩm");
                        productService.searchProduct();
                        break;
                    case 6:
                        System.out.println("Hiển thị sản phẩm theo tên A-Z");
                        productService.sortByName();
                        break;
                    case 7:
                        System.out.println("Hiển thị sản phẩm theo lợi nhuận từ cao-thấp");
                        productService.sortByProfit();
                        break;
                    case 8:
                        check = false;
                        break;
                    default:
                        System.out.println("Chọn sai mời chọn lại");
                }
            }catch (NumberFormatException e){
                System.err.println("Lựa chọn phải là số nguyên từ 1 - 8");

            }
        } while (check);
    }
}
