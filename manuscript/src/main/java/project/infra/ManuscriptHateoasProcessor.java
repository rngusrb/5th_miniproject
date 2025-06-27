package project.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import project.domain.*;

@Component
public class ManuscriptHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Manuscript>> {

    @Override
    public EntityModel<Manuscript> process(EntityModel<Manuscript> model) {
        return model;
    }
}
