#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <string>
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
    ui->tableView->horizontalHeader()->setSectionResizeMode(QHeaderView::Stretch);      // tableview를 가로폭에 딱 맞게 설정
    ui->tabWidget->setCurrentIndex(0);                                                  // 프로그램 시작 시 첫 화면을 첫번째 화면이 나오게 설정
    addCategory();
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::addCategory()
{
    std::string category[] = {"상록수","활엽수","화목류","유실수","특/약용수","덩굴류","분재류","지피류/야생화","남부수종","단풍류",
                          "--------------","옥상녹화용","공기정화용","생울타리용","준공용","산림복구용","가정용",
                          "--------------","습지/수생식물","구근류","다육식물","허브류",
                          "--------------","묘목","포트묘",
                          "--------------","공사목","조형목","특수목"};

    std::string h[] = {"0.1m","0.15m","0.2m","0.3m","0.4m","0.5m","0.6m","0.7m","0.8m","0.9m","1m","1.2m","1.5m","1.8m","2m","2.5m","3m","3.5m","4m","4.5m","5m","5.5m","6m","7m","8m","9m","10m","15m","20m","20m 이상"};
    std::string r[] = {"0.5m","1m","1.5m","2m","2.5m","3m","4m","5m","6m","7m","8m","9m","10m","11m","12m","13m","14m","15m","16m","17m","18m","19m","20m","25m","30m","35m","40m","50m","60m","70m","80m","90m","100cm 이상"};
    std::string b[] = {"0.5m","1m","1.5m","2m","2.5m","3m","3.5m","4m","5m","6m","7m","8m","9m","10m","11m","12m","13m","14m","15m","16m","17m","18m","19m","20m","25m","30m","35m","40m","50m","60m","70m","80m","90m","100cm 이상"};
    std::string w[] = {"0.2m","0.3m","0.4m","0.5m","0.6m","0.7m","0.8m","0.9m","1m","1.2m","1.5m","1.8m","2m","2.5m","3m","3.5m","4m","5m","6m","7m","8m","9m","10m 이상"};
    std::string l[] = {"0.2m","0.3m","0.4m","0.5m","0.6m","0.7m","0.8m","0.9m","1m","1.2m","1.5m","1.8m","2m","2.5m","3m","3.5m","4m","5m","6m","7m","8m","9m","11m 이상"};
    std::string t[] = {"0.5m","0.6m","0.7m","0.8m","0.9m","1m","1.2m","1.5m","1.8m","2m","2.5m","3m","3.5m","4m","4.5m","5m 이상"};
    std::string manner[] = {"실생번식", "삽목번식", "접목번식", "분주번식"};
    std::string keyword[] = {"가","나","다","라","마","바","사","아","자","차","카","타","파","하"};
    std::string location[] = {"매장","군내면 밭"};

    for(int i = 0 ; i < sizeof(category)/sizeof(category[0]); i++)
    {
        ui->category_comboBox->addItem(QString::fromStdString(category[i]));
    }
    for(int i = 0 ; i < sizeof(h)/sizeof(h[0]); i++)
    {
        ui->h_comboBox->addItem(QString::fromStdString(h[i]));
    }
    for(int i = 0 ; i < sizeof(r)/sizeof(r[0]); i++)
    {
        ui->r_comboBox->addItem(QString::fromStdString(r[i]));
    }
    for(int i = 0 ; i < sizeof(b)/sizeof(b[0]); i++)
    {
        ui->b_comboBox->addItem(QString::fromStdString(b[i]));
    }
    for(int i = 0 ; i < sizeof(w)/sizeof(w[0]); i++)
    {
        ui->w_comboBox->addItem(QString::fromStdString(w[i]));
    }
    for(int i = 0 ; i < sizeof(l)/sizeof(l[0]); i++)
    {
        ui->l_comboBox->addItem(QString::fromStdString(l[i]));
    }
    for(int i = 0 ; i < sizeof(t)/sizeof(t[0]); i++)
    {
        ui->t_comboBox->addItem(QString::fromStdString(t[i]));
    }
    for(int i = 0 ; i < sizeof(manner)/sizeof(manner[0]); i++)
    {
        ui->manner_comboBox->addItem(QString::fromStdString(manner[i]));
    }
    for(int i = 0 ; i < sizeof(keyword)/sizeof(keyword[0]); i++)
    {
        ui->keyword_comboBox->addItem(QString::fromStdString(keyword[i]));
    }
    for(int i = 0 ; i < sizeof(location)/sizeof(location[0]); i++)
    {
        ui->location_comboBox->addItem(QString::fromStdString(location[i]));
    }
} // comboBox 선택사항 추가
void MainWindow::on_selectButton_clicked()
{
     model->select();
}

void MainWindow::on_deleteButton_clicked()
{
    model->removeRow(ui->tableView->currentIndex().row());
    model->select();
}

void MainWindow::on_insertButton_clicked()
{
    QString name = ui->name_comboBox->currentText();
    QString category = ui->category_comboBox->currentText();
    QString keyword = ui->keyword_comboBox->currentText();
    QString number = ui->number_lineEdit->text();
    QString price = ui->price_lineEdit->text();
    QString location = ui->location_comboBox->currentText();
    QString ect = ui->ect_lineEdit->text();
    QString h = ui->h_comboBox->currentText();
    QString r = ui->r_comboBox->currentText();
    QString b = ui->b_comboBox->currentText();
    QString w = ui->w_comboBox->currentText();
    QString l = ui->l_comboBox->currentText();
    QString t = ui->t_comboBox->currentText();
    QString manner = ui->manner_comboBox->currentText();

    QSqlQuery qry;
    qry.prepare(QString("insert into Tree(이름, 카테고리,키워드,보유수량,주당가격, 위치, 특이사항, 수고,근원직경,흉고직경,수관폭,수관길이,지하고,육종방법)"
                        "values('%1','%2','%3','%4','%5','%6','%7','%8','%9','%10','%11','%12','%13','%14')")
                .arg(name).arg(category).arg(keyword).arg(number).arg(price).arg(location).arg(ect).arg(h).arg(r).arg(b).arg(w).arg(l).arg(t).arg(manner));
    if( !qry.exec() )
    qDebug() << qry.lastError();
    model->select();
}
