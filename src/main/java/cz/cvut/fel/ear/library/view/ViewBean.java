package cz.cvut.fel.ear.library.view;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class ViewBean {

    public ViewBean() {
//        System.out.println("Created!");
    }

    public String getFrom() {
        return this.toString();
    }

    public String getDate() {
        return new Date().toString();
    }

}