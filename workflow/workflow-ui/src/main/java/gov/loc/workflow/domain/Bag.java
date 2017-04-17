package gov.loc.workflow.domain;

import org.springframework.stereotype.Component;

//TODO: Remove the @Component annotation. Rename it, e.g. ReceiveWorkflowForm
@Component
public class Bag {

	
	private String bagId;
	private boolean doInventory;
	private boolean doMalwareScan;
	private boolean doBagInPlace;
	private boolean doVerify;
	private boolean doWriteBagInfo;
	private boolean doCopy;
	private boolean doExport;
	private boolean doDeleteFromStaging;
	private String numberOfCopies;

	public Bag() {
	}

	public Bag(String bagId, boolean doInventory, boolean doMalwareScan, boolean doBagInPlace, boolean doVerify,
			boolean doWriteBagInfo, boolean doCopy, boolean doExport, boolean doDeleteFromStaging) {
		this.bagId = bagId;
		this.doInventory = doInventory;
		this.doMalwareScan = doMalwareScan;
		this.doBagInPlace = doBagInPlace;
		this.doVerify = doVerify;
		this.doWriteBagInfo = doWriteBagInfo;
		this.doCopy = doCopy;
		this.doExport = doExport;
		this.doDeleteFromStaging = doDeleteFromStaging;
	}

	public String getBagId() {
		return bagId;
	}

	public void setBagId(String bagId) {
		this.bagId = bagId;
	}

	public boolean isDoInventory() {
		return doInventory;
	}

	public void setDoInventory(boolean doInventory) {
		this.doInventory = doInventory;
	}

	public boolean isDoMalwareScan() {
		return doMalwareScan;
	}

	public void setDoMalwareScan(boolean doMalwareScan) {
		this.doMalwareScan = doMalwareScan;
	}

	public boolean isDoBagInPlace() {
		return doBagInPlace;
	}

	public void setDoBagInPlace(boolean doBagInPlace) {
		this.doBagInPlace = doBagInPlace;
	}

	public boolean isDoVerify() {
		return doVerify;
	}

	public void setDoVerify(boolean doVerify) {
		this.doVerify = doVerify;
	}

	public boolean isDoWriteBagInfo() {
		return doWriteBagInfo;
	}

	public void setDoWriteBagInfo(boolean doWriteBagInfo) {
		this.doWriteBagInfo = doWriteBagInfo;
	}

	public boolean isDoCopy() {
		return doCopy;
	}

	public void setDoCopy(boolean doCopy) {
		this.doCopy = doCopy;
	}

	public boolean isDoExport() {
		return doExport;
	}

	public void setDoExport(boolean doExport) {
		this.doExport = doExport;
	}

	public boolean isDoDeleteFromStaging() {
		return doDeleteFromStaging;
	}

	public void setDoDeleteFromStaging(boolean doDeleteFromStaging) {
		this.doDeleteFromStaging = doDeleteFromStaging;
	}

	public String getNumberOfCopies() {
		return numberOfCopies;
	}

	public void setNumberOfCopies(String numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}


}
