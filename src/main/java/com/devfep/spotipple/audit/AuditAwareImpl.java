package com.devfep.spotipple.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        //return null if we have anonymous user
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
