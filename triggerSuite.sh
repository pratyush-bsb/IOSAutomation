#!/bin/sh

build_type="$1"
tag_number="$2"

#cd /Users/qa-lab/.jenkins/jobs/iOS_AutomationSuite/workspace
rm -rf ./build
git fetch
git checkout origin/dev

if [ "$tag_number" != "0" ]; then
	#tag number is provided. pull code with respect to this
	git checkout $tag_number
fi

xcodebuild -scheme Hike -workspace Hike.xcworkspace clean archive -archivePath ./build/Hike -destination generic/platform=iOS
xcodebuild -exportArchive -exportFormat ipa -archivePath ./build/Hike.xcarchive -exportPath ./build/Hike.ipa -exportProvisioningProfile "iOSTeam Provisioning Profile: com.bsb.hike"
mv build/*.ipa /Users/qa-lab/Documents/iosAutomation/IOSAutomation/

cd /Users/qa-lab/Documents/iosAutomation/IOSAutomation
git pull

#parse for build number and version number

build_number_flag=false
version_number_flag=false
build_number_re="(([0-9]+[.]*)+)"
version_number_re="(([0-9]+[.]*)+)"
build_numer=""
version_number=""

while read line
do

	if [[ $build_number_flag = true ]]; then
		build_number_flag=false
		if [[ $line =~ $build_number_re ]]; then
			build_number=${BASH_REMATCH[0]}
		fi
	fi

	if [[ $version_number_flag = true ]]; then
		build_number_flag=true
		if [[ $line =~ $version_number_re ]]; then
			version_number=${BASH_REMATCH[0]}
		fi
	fi

	if [[ ($build_number_flag = true) && ($version_number_flag = true) ]]; then
		break
	fi

	if [[ $line == *"CFBundleShortVersionString"* ]]; then
		build_number_flag=true
	elif [[ $line == *"CFBundleVersion"* ]]; then
		version_number_flag=true
	fi

		#statements
done < /Users/qa-lab/.jenkins/jobs/iOS_AutomationSuite/workspace/Hike-Info.plist

#run setup here. changes in every machine

#for one-to-one and settings
./b build

#move reports folder to apache folder
mkdir -p /Library/WebServer/Documents/Reports/$build_number/$version_number/One-to-one
mv reports/html /Library/WebServer/Documents/Reports/$build_number/$version_number/One-to-one/
