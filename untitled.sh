#!/bin/sh

build_type="$1"
tag_number="$2"

if [ "$tag_number" != "0" ]; then
	#tag number is provided. pull code with respect to this
	cd /Users/kumarpratyush/.jenkins/jobs/iOS_suite_run/workspace
	rm -rf ./build
	git fetch
	git checkout dev
	git checkout tag/$tag_number
fi

#xcodebuild -scheme Hike -workspace Hike.xcworkspace clean archive -archivePath ./build/Hike
#xcodebuild -exportArchive -exportFormat ipa -archivePath ./build/Hike.xcarchive -exportPath ./build/Hike.ipa -exportProvisioningProfile "iOSTeam Provisioning Profile: com.bsb.hike"
#mv build/*.ipa /Users/kumarpratyush/Documents/workspace/IOSAutomation/Hike.ipa

#cd /Users/kumarpratyush/Document/workspace/IOSAutomation
#./b build