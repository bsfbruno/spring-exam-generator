package br.com.bruno.springjsf.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.bruno.springjsf.persistence.model.ApplicationUser;
import br.com.bruno.springjsf.persistence.repository.ApplicationUserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	private final ApplicationUserRepository applicationUserRepository;

	@Autowired
	public CustomUserDetailService(ApplicationUserRepository applicationUserRepository) {
		this.applicationUserRepository = applicationUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApplicationUser applicationUser = loadApplicationUserByUsername(username);
		return new CustomUserDetails(applicationUser);
	}

	public ApplicationUser loadApplicationUserByUsername(String username) {
		return Optional.ofNullable(applicationUserRepository.findByUsername(username))
				.orElseThrow(() -> new UsernameNotFoundException("ApplicationUser not found"));
	}
	
	private final static class CustomUserDetails extends ApplicationUser implements UserDetails {

		private static final long serialVersionUID = 1L;

		private CustomUserDetails(ApplicationUser applicationUser) {
			super(applicationUser);			
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			List<GrantedAuthority> authorityListProfessor = AuthorityUtils.createAuthorityList("ROLE_PROFESSOR");
			List<GrantedAuthority> authorityListStudent = AuthorityUtils.createAuthorityList("ROLE_STUDENT");
			return this.getProfessor() != null ? authorityListProfessor : authorityListStudent;
		}
				
		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return true;
		}
		
	}
}
