package ca.homedepot.customerreview.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.homedepot.customerreview.dao.CustomerReviewDao;
import ca.homedepot.customerreview.model.CustomerReviewModel;
import ca.homedepot.customerreview.model.LanguageModel;
import ca.homedepot.customerreview.model.ProductModel;
import ca.homedepot.customerreview.model.UserModel;
import ca.homedepot.customerreview.service.CustomerReviewService;
import ca.homedepot.customerreview.util.ServicesUtil;


@Component
public class DefaultCustomerReviewService implements CustomerReviewService
{
	private CustomerReviewDao customerReviewDao;

	@Autowired
	public DefaultCustomerReviewService(CustomerReviewDao customerReviewDao)
	{
		this.customerReviewDao = customerReviewDao;
	}

	@Override
	public CustomerReviewModel createCustomerReview(final Double rating, final String headline, final String comment,
			final ProductModel product, final UserModel user)
	{
		final CustomerReviewModel review = new CustomerReviewModel();
		review.setRating(rating);
		review.setHeadline(headline);
		review.setComment(comment);
		review.setProduct(product);
		review.setUser(user);
		
		customerReviewDao.save(review);
		return review;
	}

	@Override
	public void updateCustomerReview(final CustomerReviewModel review, UserModel user, ProductModel product)
	{
		customerReviewDao.save(review);
	}

	@Override
	public List<CustomerReviewModel> getReviewsForProduct(final ProductModel product)
	{
		ServicesUtil.validateParameterNotNullStandardMessage("product", product);
		List<CustomerReviewModel> result = customerReviewDao.getAllReviews(product);
		
		
		return result;
	}

	@Override
	public Double getAverageRating(final ProductModel product)
	{
		return customerReviewDao.getAverageRating(product);
	}

	@Override
	public Integer getNumberOfReviews(final ProductModel product)
	{
		return customerReviewDao.getNumberOfReviews(product);
	}

	@Override
	public List<CustomerReviewModel> getReviewsForProductAndLanguage(final ProductModel product, final LanguageModel language)
	{
		ServicesUtil.validateParameterNotNullStandardMessage("product", product);
		ServicesUtil.validateParameterNotNullStandardMessage("language", language);
		return customerReviewDao.getReviewsForProductAndLanguage(product, language);
	}

	@Override
	public void deleteCustomerReview(final Long id)
	{
		customerReviewDao.deleteReview(id);
	}

	@Override
	public List<CustomerReviewModel> getReviewsForProduct(ProductModel product, Double ratingFrom, Double ratingTo) {
		ServicesUtil.validateParameterNotNullStandardMessage("product", product);
		List<CustomerReviewModel> result = customerReviewDao.getAllReviews(product);
		List<CustomerReviewModel> finalResult = new ArrayList<CustomerReviewModel> ();
		for(CustomerReviewModel obj : result) {
			if(obj.getRating()>=ratingFrom && obj.getRating()<=ratingTo)
			finalResult.add(obj);
		}
		
		return finalResult;
	}
}
