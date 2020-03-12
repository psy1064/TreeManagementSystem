#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    qDebug() << "드라이버" << QSqlDatabase::drivers();
    qDebug() << QCoreApplication::libraryPaths();
    db = QSqlDatabase::addDatabase("QMYSQL");
    db.setHostName("127.0.0.1");      // IP 또는 DNS Host name
    db.setPort(3306);
    db.setDatabaseName("tree"); // DB명
    db.setUserName("root");     // 계정 명
    db.setPassword("12345678");     // 계정 Password

    db.open();

    model = new QSqlTableModel(this);
    model->setTable("Tree");    // 테이블명
    ui->tableView->setModel(model);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_pushButton_clicked()
{
    model->select();
}
