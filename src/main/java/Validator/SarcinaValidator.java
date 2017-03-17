package Validator;

import Domain.Sarcina;

public class SarcinaValidator implements IValidator<Sarcina> {


    @Override
    public void validate(Sarcina sarcina)throws ValidationException {
        String msg = new String();

        if(sarcina.getID()<0)
            msg+="ID-ul sarcinii nu poate fi negativ\n";

        if(sarcina.getDescription() == null || sarcina.getDescription() == "")
            msg+="Descrierea sarcinii nu poate fi vida\n";

        if(msg.length()>0)
            throw new ValidationException(msg);
    }
}

