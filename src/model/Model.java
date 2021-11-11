package model;

public class Model {
    private String modelId;
    private String modelName;

    public Model() {
    }

    public Model(String modelId, String modelName) {
        this.setModelId(modelId);
        this.setModelName(modelName);
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public String toString() {
        return "Model{" +
                "modelId='" + modelId + '\'' +
                ", modelName='" + modelName + '\'' +
                '}';
    }
}
