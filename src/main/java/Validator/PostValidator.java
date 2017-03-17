package Validator;

import Domain.Post;

public class PostValidator implements IValidator<Post> {


    @Override
    public void validate(Post post) throws ValidationException {
        String msg = new String();

        if(post.getID()<0)
            msg+="ID-ul postului nu poate fi negativ\n";

        if(post.getName() == null || post.getName() == "")
            msg+="Numele postului nu poate fi vid\n";

        if(post.getTip() == null || post.getTip() == "")
            msg+="Tipul postului nu poate fi vid\n";

        if(msg.length()>0)
            throw new ValidationException(msg);
    }
}
