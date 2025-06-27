package project.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import project.domain.*;

@Component
public class PlatformHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Platform>> {

    @Override
    public EntityModel<Platform> process(EntityModel<Platform> model) {
        return model;
    }
}
