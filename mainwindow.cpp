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
    model->select();
    ui->tableView->setModel(model);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_selectButton_clicked()
{
     model->select();
}

void MainWindow::on_deleteButton_clicked()
{
    QModelIndexList selection = ui->tableView->selectionModel()->selectedRows();
    // Multiple rows can be selected
    for(int i=0; i< selection.count(); i++)
    {
        QModelIndex index = selection.at(i);
        qDebug() << index.row();
    }

}

void MainWindow::on_insertButton_clicked()
{
    QString name = ui->nameEdit->text();
    QString category = ui->nameEdit->text();

    QSqlQuery qry;
    qry.prepare(QString("insert into Tree(Name, Category) values('%1','%2')")
                .arg(name).arg(category));
    if( !qry.exec() )
    qDebug() << qry.lastError();
}
