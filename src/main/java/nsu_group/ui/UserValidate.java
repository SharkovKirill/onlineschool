package nsu_group.ui;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidate implements Validator{
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
            errors.rejectValue("name", "Unavailable name");
        }

        if (user.getPassword().length()<4){

            errors.rejectValue("password", "Unavailable password");
        }
//        if (check() == true) {
//            errors.rejectValue("name", "User is created");
//        }



        // do "complex" validation here


    }}

