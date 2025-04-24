package tn.esprit.pidevspringboot.Entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PlatAssociationRequest {
    private List<Long> platIds;
}
