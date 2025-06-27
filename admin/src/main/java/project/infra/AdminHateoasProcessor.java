package project.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import project.domain.*;

@Component
public class AdminHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Admin>> {

    @Override
    public EntityModel<Admin> process(EntityModel<Admin> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/registerbook")
                .withRel("registerbook")
        );

        return model;
    }
}
