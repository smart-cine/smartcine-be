package org.example.cinemamanagement.auth;


import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.example.cinemamanagement.common.BusinessRole;
import org.example.cinemamanagement.model.BusinessOwnership;
import org.example.cinemamanagement.model.OwnerShipTree;
import org.example.cinemamanagement.repository.BusinessOwnershipRepository;
import org.example.cinemamanagement.repository.OwnerShipTreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class AuthorizationService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private BusinessOwnershipRepository businessOwnershipRepository;
    @Autowired
    private OwnerShipTreeRepository ownerShipTreeRepository;

    public boolean canAccessTo(UUID accountId, UUID itemId, String feature) {
        try {
            BusinessRole role = getRoleWithParentEntityOfItem(accountId, itemId);
            if (role == null)
                return false;

            String sql = "SELECT feature FROM _role_feature WHERE role = :role AND feature = :feature";

                Object roleFeature = entityManager.createNativeQuery(sql)
                        .setParameter("role", role.name())
                        .setParameter("feature", feature)
                        .getSingleResult();
                return roleFeature != null;

        }
        catch (NoResultException e)
        {
            throw new RuntimeException("Don't have permission on your role");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private BusinessRole getRoleWithParentEntityOfItem(UUID accountId, UUID itemId) {
        try {
            BusinessOwnership businessOwnership = businessOwnershipRepository.findByOwnerId(accountId).orElseThrow(
                    () -> new RuntimeException("Don't have permission")
            );

            while (true) {
                OwnerShipTree ownerShipTree = ownerShipTreeRepository.findByItemId(itemId).orElseThrow(
                        () -> new RuntimeException("Item not found")
                );
//                System.out.println(ownerShipTree.getParentId() + " " + ownerShipTree.getItemId());

                if (ownerShipTree.getParentId() == null)
                    return null;

                if (ownerShipTree.getParentId().equals(businessOwnership.getItemId()))
                    return businessOwnership.getRole();

                itemId = ownerShipTree.getParentId();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}