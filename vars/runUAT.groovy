boolean BuildCookAndRunUAT(Map args = [:]) {
    String buildCommandString = ''

    // validate the path of the runUAT batch (optional)
    String runUATBatchPath = args.get('runUATPath', 'Engine/Build/BatchFiles/RunUAT.bat')
    if (!runUATBatchPath?.trim()) {
        echo 'BuildCookAndRunUAT exited with an error: runUATPath cannot be empty!'
        return false
    }
    if (!runUATPath.contains('RunUAT.bat')) {
        echo 'BuildCookAndRunUAT exited with an error: runUATPath have to contain \"RunUAT.bat\"!'
        return false
    }

    buildCommandString += "${runUATBatchPath} BuildCookRun"

    // validate project path (required)
    String project = args.get('project', null)
    if (!project?.trim()) {
        echo 'BuildCookAndRunUAT exited with an error: project argument is required but found empty!'
        return false
    }

    buildCommandString += " -project=\"${project}\""

    //target validation (required)
    String targetArgument = args.get('target')
    if (!targetArgument?.trim()) {
        echo 'BuildCookAndRunUAT exited with an error: target argument is required but found empty!'
        return false
    }

    // add some fix parameters, which are not parametized atm
    buildCommandString += ' -build -cook -compile -iterate -stage -package -pak'

    // platform (optional)
    String platformArgument = args.get('platform', 'Win64')
    buildCommandString += " -platform=${platformArgument}"

    //clientconfig (optional)
    String clientConfigArgument = args.get('clientconfig', 'Development')
    buildCommandString += " -clientconfig=${clientConfigArgument}"

    //clientconfig (optional)
    String archiveDirectoryArgument = args.get('archivedirectory', "${WORKSPACE}\\Build")
    buildCommandString += " -archive -archivedirectory=\"${archiveDirectoryArgument}\""

    // execute the batch with all arguments
    echo "Run command ${buildCommandString}"
    bat "${buildCommandString}"
    return true
}

boolean BuildCookAndRunUAT(String projectPath) {
    Map argumentMap = [
        'project' : projectPath
    ]
    return runUAT.BuildCookAndRunUAT(argumentMap)
}
