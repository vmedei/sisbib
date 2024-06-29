module br.ufrn.imd.lp2.projeto03 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens br.ufrn.imd.lp2.projeto03 to javafx.fxml;
    exports br.ufrn.imd.lp2.projeto03;
    
    opens br.ufrn.imd.lp2.projeto03.controller to javafx.fxml;
    exports br.ufrn.imd.lp2.projeto03.controller;

    opens br.ufrn.imd.lp2.projeto03.models to javafx.fxml;
    exports br.ufrn.imd.lp2.projeto03.models;

    opens br.ufrn.imd.lp2.projeto03.DAO to javafx.fxml;
    exports br.ufrn.imd.lp2.projeto03.DAO;

}
