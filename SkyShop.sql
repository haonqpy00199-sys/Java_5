-- 1. Tạo Database
CREATE DATABASE Java5_AmazonDB;
GO
USE Java5_AmazonDB;
GO

-- 2. Bảng Loại hàng (Categories)
CREATE TABLE Categories (
    Id CHAR(4) PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL
);

-- 3. Bảng Tài khoản (Accounts)
CREATE TABLE Accounts (
    Username NVARCHAR(50) PRIMARY KEY,
    Password NVARCHAR(50) NOT NULL,
    Fullname NVARCHAR(50) NOT NULL,
    Email NVARCHAR(50) NOT NULL,
    Photo NVARCHAR(50) NULL,
    Activated BIT NOT NULL DEFAULT 1,
    Admin BIT NOT NULL DEFAULT 0
);

-- 4. Bảng Sản phẩm (Products)
CREATE TABLE Products (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(100) NOT NULL,
    Image NVARCHAR(50) NULL,
    Price FLOAT NOT NULL DEFAULT 0,
    CreateDate DATE NOT NULL DEFAULT GETDATE(),
    Available BIT NOT NULL DEFAULT 1,
    CategoryId CHAR(4) NOT NULL,
    FOREIGN KEY (CategoryId) REFERENCES Categories(Id)
);

-- 5. Bảng Đơn hàng (Orders)
CREATE TABLE Orders (
    Id BIGINT IDENTITY(1,1) PRIMARY KEY,
    Username NVARCHAR(50) NOT NULL,
    CreateDate DATETIME NOT NULL DEFAULT GETDATE(),
    Address NVARCHAR(255) NOT NULL,
    FOREIGN KEY (Username) REFERENCES Accounts(Username)
);

-- 6. Bảng Chi tiết đơn hàng (OrderDetails)
CREATE TABLE OrderDetails (
    Id BIGINT IDENTITY(1,1) PRIMARY KEY,
    OrderId BIGINT NOT NULL,
    ProductId INT NOT NULL,
    Price FLOAT NOT NULL,
    Quantity INT NOT NULL,
    FOREIGN KEY (OrderId) REFERENCES Orders(Id),
    FOREIGN KEY (ProductId) REFERENCES Products(Id)
);

-- Chèn 10 loại hàng (Categories)
INSERT INTO Categories (Id, Name) VALUES 
('ELEC', N'Electronics'), ('FASH', N'Fashion'), ('HOME', N'Home & Kitchen'),
('BOOK', N'Books'), ('BEAU', N'Beauty & Personal Care'), ('TOYS', N'Toys & Games'),
('SPOR', N'Sports & Outdoors'), ('TOOL', N'Tools & Improvement'), ('AUTO', N'Automotive'),
('GROC', N'Grocery');

-- Chèn 5 người dùng và 1 Admin (Accounts)
INSERT INTO Accounts (Username, Password, Fullname, Email, Photo, Activated, Admin) VALUES 
('admin', '123', N'Quản trị viên', 'admin@amazon.com', 'admin.jpg', 1, 1),
('user1', '123', N'Nguyễn Văn A', 'vana@gmail.com', 'user1.jpg', 1, 0),
('user2', '123', N'Trần Thị B', 'thib@gmail.com', 'user2.jpg', 1, 0),
('user3', '123', N'Lê Văn C', 'vanc@gmail.com', 'user3.jpg', 1, 0),
('user4', '123', N'Phạm Minh D', 'minhd@gmail.com', 'user4.jpg', 1, 0),
('user5', '123', N'Hoàng Anh E', 'anhe@gmail.com', 'user5.jpg', 1, 0);

-- Chèn một vài sản phẩm mẫu (Products)
INSERT INTO Products (Name, Image, Price, CreateDate, Available, CategoryId) VALUES 
(N'iPhone 15 Pro Max', 'iphone15.jpg', 1200, GETDATE(), 1, 'ELEC'),
(N'Samsung Galaxy S24', 's24.jpg', 1100, GETDATE(), 1, 'ELEC'),
(N'Nike Air Force 1', 'nike_af1.jpg', 100, GETDATE(), 1, 'FASH'),
(N'Instant Pot Duo', 'cooker.jpg', 80, GETDATE(), 1, 'HOME');