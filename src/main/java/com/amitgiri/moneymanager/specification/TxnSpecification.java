package com.amitgiri.moneymanager.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.amitgiri.moneymanager.dto.TxnFilterDto;
import com.amitgiri.moneymanager.entity.Txn;

import jakarta.persistence.criteria.Predicate;

public class TxnSpecification {

    public static Specification<Txn>
    withFilters(
            Long userId,
            TxnFilterDto filter
    ) {

        return (root, query, cb) -> {

            List<Predicate> predicates =
                    new ArrayList<>();

            predicates.add(
                    cb.equal(
                            root.get("user").get("id"),
                            userId
                    )
            );



            if(filter.getTxnTypeId() != null) {

                predicates.add(
                        cb.equal(
                                root.get("txnType").get("id"),
                                filter.getTxnTypeId()
                        )
                );
            }

            if(filter.getTxnCategoryId() != null) {

                predicates.add(
                        cb.equal(
                                root.get("txnCategory").get("id"),
                                filter.getTxnCategoryId()
                        )
                );
            }

            if(filter.getKeyword() != null) {

                predicates.add(
                        cb.like(
                                cb.lower(root.get("note")),
                                "%" +
                                filter.getKeyword()
                                        .toLowerCase()
                                + "%"
                        )
                );
            }

            return cb.and(
                    predicates.toArray(
                            new Predicate[0]
                    )
            );
        };
    }
}
