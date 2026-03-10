# Lab 7: RecyclerView and SQLite CRUD

Ứng dụng quản lý sinh viên sử dụng RecyclerView để hiển thị danh sách dữ liệu tùy biến và SQLite để lưu trữ dữ liệu bền vững.

## Mục tiêu
Hiển thị danh sách dữ liệu tùy biến và thực hiện đầy đủ các chức năng CRUD (Create, Read, Update, Delete).

## Yêu cầu thực hiện
1.  **Class Student**: Chứa các thuộc tính `Id`, `Name`, `Email`.
2.  **Giao diện dòng (item_student.xml)**:
    *   `ImageView`: Ảnh đại diện mặc định.
    *   `TextView`: Hiển thị Tên.
    *   `TextView`: Hiển thị Email.
    *   `ImageButton`: Nút Sửa và Xóa.
3.  **StudentAdapter**: Kế thừa `RecyclerView.Adapter`, cài đặt `ViewHolder` để tối ưu hóa hiệu suất hiển thị.
4.  **DatabaseHelper**: Quản lý cơ sở dữ liệu SQLite, thực hiện các thao tác Thêm, Xem, Sửa, Xóa.
5.  **MainActivity**:
    *   Sử dụng `RecyclerView` với `LinearLayoutManager`.
    *   Tự động khởi tạo 10 sinh viên mẫu nếu danh sách trống.
    *   Xử lý logic tương tác người dùng cho các chức năng CRUD.

## Cấu trúc Project
*   `Student.kt`: Model data.
*   `DatabaseHelper.kt`: Lớp hỗ trợ thao tác với SQLite.
*   `StudentAdapter.kt`: Adapter cho RecyclerView.
*   `MainActivity.kt`: Xử lý logic chính của ứng dụng.
*   `res/layout/activity_main.xml`: Giao diện chính của ứng dụng.
*   `res/layout/item_student.xml`: Giao diện cho từng dòng sinh viên trong danh sách.

## Hướng dẫn sử dụng
1.  Mở ứng dụng: Danh sách 10 sinh viên mặc định sẽ hiện ra.
2.  **Thêm**: Nhập Tên và Email vào các ô nhập liệu, sau đó nhấn "Add Student".
3.  **Sửa**: Nhấn biểu tượng bút chì trên dòng sinh viên cần sửa. Thông tin sẽ hiển thị lên các ô nhập. Sau khi chỉnh sửa, nhấn "Update Student".
4.  **Xóa**: Nhấn biểu tượng thùng rác trên dòng sinh viên muốn xóa.
