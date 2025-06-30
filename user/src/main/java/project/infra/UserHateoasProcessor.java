package project.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import project.domain.*;

@Component
public class UserHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<User>> {

    @Override
    public EntityModel<User> process(EntityModel<User> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/reqeustlogin")
                .withRel("reqeustlogin")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/requestuserregistration"
                )
                .withRel("requestuserregistration")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/requestsubscription"
                )
                .withRel("requestsubscription")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/cancelsubscription"
                )
                .withRel("cancelsubscription")
        );

        return model;
    }
}