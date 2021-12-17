package nsu_group.ui;

import nsu_group.ui.mvc.UserController;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidate implements Validator{
    private final InSQLUserRepository insqluserepositore = new InSQLUserRepository();
    @Override
    public boolean supports(Class clazz) {
        return User.class.equals(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;


        if((user.getEmail().matches("[A-Za-z].*?@gmail\\.com"))){
            System.out.println("Ok");
        }else {
            errors.rejectValue("email", "Unavailable email");
        }

        if (!user.getName().matches("-?\\d+(\\.\\d+)?")){
            System.out.println("Ok");
        }else {
            errors.rejectValue("name", "Неврный ввод");
        }

        if (user.getPassword().length()<4){
            errors.rejectValue("password", "Unavailable password");
        }
        if (insqluserepositore.check(user) == true) {
            errors.rejectValue("name", "User is created");
        }



        // do "complex" validation here


    }}

