package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.stereotype.Service;

@Service
public class DonationService {
	//JUNTAR LOS DOS SERVICES???
	private final DonationRepository donationRepository;
	
	//private final CauseRepository causeRepository;

	@Autowired
	public DonationService(final DonationRepository donationRepository) {
		//this.causeRepository = causeRepository;
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
	
}
