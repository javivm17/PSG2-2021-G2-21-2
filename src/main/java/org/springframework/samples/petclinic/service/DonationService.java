package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.stereotype.Service;

@Service
public class DonationService {
	
	private final DonationRepository donationRepository;

	@Autowired
	public DonationService(final DonationRepository donationRepository) {
		this.donationRepository = donationRepository;
	}
	
	@Transactional
	public void saveDonation(final Donation donation) throws DataAccessException{
		this.donationRepository.save(donation);
	}
	
	@Transactional
	public void deleteDonation(final Donation donation) throws DataAccessException{
		this.donationRepository.delete(donation);
	}
	
	public Donation findDonationById(final int id) throws DataAccessException {
		return this.donationRepository.findById(id);
	}

	public Collection<Donation> findDonationsByCauseId(final int causeId) throws DataAccessException{
		return this.donationRepository.findByCauseId(causeId);
	}
	
	public List<Owner> findOwners() throws DataAccessException{
		return this.donationRepository.findOwners();
	}
	
}
