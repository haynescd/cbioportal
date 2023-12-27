package org.cbioportal.persistence.mysql;

import org.cbioportal.model.*;
import org.cbioportal.persistence.MolecularDataRepository;
import org.cbioportal.web.parameter.StudyViewFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@Profile("mysql")
public class MolecularDataMyBatisRepository implements MolecularDataRepository {

    @Autowired
    private MolecularDataMapper molecularDataMapper;

    @Override
    public MolecularProfileSamples getCommaSeparatedSampleIdsOfMolecularProfile(String molecularProfileId) {
        try {
            return molecularDataMapper.getCommaSeparatedSampleIdsOfMolecularProfiles(
                Collections.singleton(molecularProfileId)).get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public Map<String, MolecularProfileSamples> commaSeparatedSampleIdsOfMolecularProfilesMap(Set<String> molecularProfileIds) {

        return molecularDataMapper.getCommaSeparatedSampleIdsOfMolecularProfiles(molecularProfileIds)
                .stream()
                .collect(Collectors.toMap(MolecularProfileSamples::getMolecularProfileId, Function.identity()));
    }

    @Override
    public List<GeneMolecularAlteration> getGeneMolecularAlterations(String molecularProfileId, 
                                                                     List<Integer> entrezGeneIds, String projection) {

        return molecularDataMapper.getGeneMolecularAlterations(molecularProfileId, entrezGeneIds, projection);
    }

    @Override
    // In order to return a cursor/iterator to the service layer, we need a transaction setup in the service
    // layer. Currently, the bottom stackframe is CoExpressionService:getCoExpressions.  It is there where
    // you will find the transaction created.
    public Iterable<GeneMolecularAlteration> getGeneMolecularAlterationsIterable(String molecularProfileId, 
                                                                                 List<Integer> entrezGeneIds, String projection) {

        return molecularDataMapper.getGeneMolecularAlterationsIter(molecularProfileId, entrezGeneIds, projection);
    }

    @Override
    public Iterable<GeneMolecularAlteration> getGeneMolecularAlterationsIterableFast(String molecularProfileId) {

        return molecularDataMapper.getGeneMolecularAlterationsIterFast(molecularProfileId);
    }

    @Override
    public List<GeneMolecularAlteration> getGeneMolecularAlterationsInMultipleMolecularProfiles(Set<String> molecularProfileIds, 
                                                                                                List<Integer> entrezGeneIds, 
                                                                                                String projection) {

        return molecularDataMapper.getGeneMolecularAlterationsInMultipleMolecularProfiles(molecularProfileIds, entrezGeneIds, 
                projection);
	}

    @Override
	public List<GenesetMolecularAlteration> getGenesetMolecularAlterations(String molecularProfileId, 
                                                                           List<String> genesetIds, String projection) {

		return molecularDataMapper.getGenesetMolecularAlterations(molecularProfileId, genesetIds, projection);
    }
    
    @Override
    public List<GenericAssayMolecularAlteration> getGenericAssayMolecularAlterations(String molecularProfileId, List<String> stableIds, String projection) {
        return molecularDataMapper.getGenericAssayMolecularAlterations(molecularProfileId, stableIds, projection);
    }

	@Override
	public Iterable<GenericAssayMolecularAlteration> getGenericAssayMolecularAlterationsIterable(
			String molecularProfileId, List<String> stableIds, String projection) {
		return molecularDataMapper.getGenericAssayMolecularAlterationsIter(molecularProfileId, stableIds, projection);
	}

	@Override
	public List<GenomicDataCount> getMolecularProfileSampleCounts(StudyViewFilter interceptedStudyViewFilter,
			boolean singleStudyUnfiltered) {
		// TODO Auto-generated method stub
		return null;
	}
}
